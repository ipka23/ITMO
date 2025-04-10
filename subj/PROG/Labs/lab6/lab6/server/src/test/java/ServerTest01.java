import common_utility.network.Request;
import common_utility.network.Response;
import lombok.extern.slf4j.Slf4j;
import server_commands.Add;
import server_commands.ExecuteScript;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_managers.FileManager;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class ServerTest01 {
    public static int PORT = 1123;
    private static ServerSocket serverSocket;
    private static String collectionFileName;
    private static Invoker invoker;
    private static ClientConsole console;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;

    // Параметры подключения к БД
    private static final String DB_URL = "jdbc:postgresql://localhost:2323/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "ты, скрытный тип, напиши сюда свой пароль от бд";
    private static Connection dbConnection;

    // Пул потоков для обработки клиентов
    private static ExecutorService clientThreadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try {
            // Инициализация подключения к БД
            initializeDatabase();

            // Инициализация компонентов сервера
            fileManager = new FileManager(null, console, null);
            collectionManager = new CollectionManager(fileManager, console);
            commandManager = new CommandManager(console, collectionManager);
            invoker = new Invoker(commandManager, console);

            console = new ClientConsole(invoker);

            fileManager.setCollectionManager(collectionManager);
            console.setCollectionManager(collectionManager);

            run();
        } catch (SQLException e) {
            log.error("Database initialization failed: {}", e.getMessage());
        } finally {
            shutdown();
        }
    }

    public static void run() {
        log.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                log.info("New client connection accepted");

                // Обработка каждого клиента в отдельном потоке
                clientThreadPool.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            log.error("Server error: {}", e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (ObjectOutputStream outToClient = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
             ObjectInputStream inFromClient = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()))) {

            outToClient.flush();

            // Аутентификация пользователя
            if (!authenticateUser(inFromClient, outToClient)) {
                log.warn("Authentication failed for client");
                return;
            }

            // Получение файла коллекции
            collectionFileName = getFile(inFromClient, outToClient);
            if (collectionFileName == null) {
                return;
            }

            fileManager.setFile(collectionFileName);

            // Настройка консоли для клиента
            ClientConsole clientConsole = new ClientConsole(invoker, collectionManager, inFromClient, outToClient);
            clientConsole.setObjectInputStream(inFromClient);
            clientConsole.setObjectOutputStream(outToClient);

            // Добавление команд
            commandManager.addCommand("add", new Add(clientConsole, collectionManager, inFromClient, outToClient));
            commandManager.addCommand("execute_script", new ExecuteScript(clientConsole, invoker, inFromClient, outToClient));

            // Запуск обработки команд
            clientConsole.launch();

        } catch (IOException | ClassNotFoundException e) {
            log.error("Client handling error: {}", e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                log.error("Error closing client socket: {}", e.getMessage());
            }
        }
    }

    private static boolean authenticateUser(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        try {
            // Получаем данные аутентификации от клиента
            Request authRequest = (Request) in.readObject();
            String[] authData = authRequest.getMessage().split(":");
            String username = authData[0];
            String password = authData[1];

            // Проверяем учетные данные в БД
            PreparedStatement stmt = dbConnection.prepareStatement(
                    "SELECT password_hash, salt FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String salt = rs.getString("salt");

                // Хешируем предоставленный пароль с солью
                String inputHash = hashPassword(password, salt);

                if (inputHash.equals(storedHash)) {
                    out.writeObject(new Response(false, "Authentication successful"));
                    out.flush();
                    return true;
                }
            }

            out.writeObject(new Response(true, "Authentication failed"));
            out.flush();
            return false;

        } catch (SQLException e) {
            log.error("Database error during authentication: {}", e.getMessage());
            out.writeObject(new Response(true, "Server error during authentication"));
            out.flush();
            return false;
        } catch (NoSuchAlgorithmException e) {
            log.error("Password hashing error: {}", e.getMessage());
            out.writeObject(new Response(true, "Server error during authentication"));
            out.flush();
            return false;
        }
    }

    private static String getFile(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Request request = (Request) in.readObject();
        String fileName = request.getMessage();
        Response response;

        try {
            if (fileName.isEmpty()) {
                response = new Response(true, "Введите имя файла как аргумент командной строки!");
            } else if (!new File(fileName).exists()) {
                response = new Response(true, "Файл \"" + fileName + "\" не найден!!!!");
            } else if (!new File(fileName).canRead()) {
                response = new Response(true, "Нет прав на чтение файла \"" + fileName + "\"!");
            } else {
                response = new Response(false);
                log.info("File received: {}", fileName);
            }
            out.writeObject(response);
            out.flush();
            return fileName;
        } catch (Exception e) {
            log.error("Error processing file request: {}", e.getMessage());
            out.writeObject(new Response(true, "Error processing file request"));
            out.flush();
            return null;
        }
    }

    private static void initializeDatabase() throws SQLException {
        try {
            dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            log.info("Database connection established");

            // Создание таблицы пользователей, если её нет
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password_hash VARCHAR(128) NOT NULL, " +
                    "salt VARCHAR(64) NOT NULL)";

            dbConnection.createStatement().execute(createTableSQL);
            log.info("Users table verified/created");

        } catch (SQLException e) {
            log.error("Database initialization failed: {}", e.getMessage());
            throw e;
        }
    }

    public static boolean registerUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
        // Генерируем соль
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[32];
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);

        // Хешируем пароль с солью
        String hashedPassword = hashPassword(password, salt);

        // Сохраняем пользователя в БД
        PreparedStatement stmt = dbConnection.prepareStatement(
                "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        stmt.setString(3, salt);

        try {
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Ошибка уникальности
                log.warn("Username {} already exists", username);
                return false;
            }
            throw e;
        }
    }

    private static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    private static void shutdown() {
        log.info("Shutting down server...");
        clientThreadPool.shutdown();

        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (SQLException | IOException e) {
            log.error("Error during shutdown: {}", e.getMessage());
        }
    }
}