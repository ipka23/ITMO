import common_utility.network.Request;
import common_utility.network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_managers.FileManager;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;
import server_utility.consoles.ServerConsole;
import server_utility.database.DatabaseManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public int PORT = 1232;
    private Socket clientSocket;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private String collectionFileName;
    private Invoker invoker;
    private ClientConsole clientConsole;
    private FileManager fileManager;
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    private Logger log = LoggerFactory.getLogger("ServerConsole");
    private StringBuilder credentials = new StringBuilder();
    private String username = null;
    private String password = null;
    private String url = "jdbc:postgresql://localhost:15432/studs";
    private final static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Server server = new Server();
        server.initializeServer();
        server.run();
    }

    private void initializeServer() {
//        dbHandler = new DBHandler("")
        fileManager = new FileManager(null, clientConsole, null);
        collectionManager = new CollectionManager(clientConsole);

        commandManager = new CommandManager();
        invoker = new Invoker(commandManager, clientConsole);

        clientConsole = new ClientConsole(invoker);

        fileManager.setCollectionManager(collectionManager);
        clientConsole.setCollectionManager(collectionManager);


    }

    public void run() {
        log.info("Server has started on port: {}", PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                clientSocket = serverSocket.accept();
                executor.submit(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

            }


        } catch (Exception e) {
            log.error("Server error", e);
        }
        executor.shutdown();
    }

    private void handleClient(Socket clientSocket) throws IOException, ClassNotFoundException {
        while (!Thread.currentThread().isInterrupted()) {
            log.info("Client has connected! Host: {}", clientSocket.getInetAddress().getHostAddress());
            outToClient = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            outToClient.flush();
            clientConsole.setObjectOutputStream(outToClient);
            inFromClient = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            clientConsole.setObjectInputStream(inFromClient);
            log.info("Successfully declared in & out streams");
            collectionFileName = getFile();
            fileManager.setFile(collectionFileName);
            commandManager.declareCommands(clientConsole, collectionManager, invoker, inFromClient, outToClient, log);
            connect();
            Runnable serverConsole = new ServerConsole(collectionManager);
            new Thread(serverConsole).start();
            clientConsole.launch();
        }
    }


    private String getFile() throws IOException, ClassNotFoundException {
        Request request = (Request) inFromClient.readObject();
        collectionFileName = request.getMessage();
        Response response;
        try {
            if (collectionFileName.isEmpty()) {
                response = new Response(true, "Введите имя файла как аргумент командной строки!");
            } else if (!new File(collectionFileName).exists()) {
                response = new Response(true, "Файл \"" + collectionFileName + "\" не найден!!!!");
            } else if (!new File(collectionFileName).canRead()) {
                response = new Response(true, "Нет прав на чтение файла \"" + collectionFileName + "\"!");
            } else {
                response = new Response(false);
                log.info("File received: {}", collectionFileName);
            }
            outToClient.writeObject(response);
            outToClient.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return collectionFileName;
    }
    public void connect() {
        try (Scanner scanner = new Scanner(new FileReader("credentials.txt"))) {
            String line = scanner.nextLine().trim();
            while (scanner.hasNextLine()){
                if (!line.isEmpty()) {
                    username = line;
                    line = scanner.nextLine().trim();
                }
                if (!line.isEmpty()) {
                    password = line;
                }
                log.info("Credentials have been received!");
                DatabaseManager dbManager = new DatabaseManager(url, username, password);
                dbManager.setCollectionManager(collectionManager);
                collectionManager.setDatabaseManager(dbManager);
                dbManager.connectToDB();
            }
        } catch (FileNotFoundException e) {
            log.error("Database credentials file not found!");
            System.exit(1);
        } catch (NullPointerException e) {
            log.error("Incorrect database credentials!");
            System.exit(1);
        }
    }
}
