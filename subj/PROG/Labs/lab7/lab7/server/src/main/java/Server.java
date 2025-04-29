import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;
import server_managers.DatabaseManager;
import server_managers.UserManager;

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
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    private Logger log = LoggerFactory.getLogger("Server");
    private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Server server = new Server();
        server.initializeServer();
        server.run();
    }

    private void initializeServer() {
        collectionManager = new CollectionManager(clientConsole);

        commandManager = new CommandManager();
        invoker = new Invoker(commandManager, clientConsole);

        clientConsole = new ClientConsole(invoker);

        clientConsole.setCollectionManager(collectionManager);
    }


    public void run() {
        log.info("Server has started on port: {}", PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                clientSocket = serverSocket.accept();
                cachedThreadPool.submit(() -> handleClient(clientSocket));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        cachedThreadPool.shutdown();
    }

    private void handleClient(Socket clientSocket) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                log.info("Client has connected! Host: {}", clientSocket.getInetAddress().getHostAddress());
                outToClient = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                synchronized (outToClient) {
                    outToClient.flush();  // чтобы не возникала блокировка потока со стороны клиента
                }
                inFromClient = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                log.info("Successfully declared in & out streams");
                commandManager.declareCommands(clientConsole, collectionManager, invoker, inFromClient, outToClient, log);
                connect();
                clientConsole.run(inFromClient, outToClient);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    public void connect() {
        try (Scanner scanner = new Scanner(new FileReader("/Users/ipka23/Desktop/ITMO/subj/PROG/Labs/lab7/lab7/server/src/test/resources/credentials.txt"))) {
            String DB_USERNAME = "";
            String DB_URL = "jdbc:postgresql://localhost:15432/studs";
            String DB_PASSWORD = "";
            String line = scanner.nextLine().trim();
            while (scanner.hasNextLine()) {
                if (!line.isEmpty()) {
                    DB_USERNAME = line;
                    line = scanner.nextLine().trim();
                }
                if (!line.isEmpty()) {
                    DB_PASSWORD = line;
                }
                log.info("Credentials have been received!");
                DatabaseManager dbManager = new DatabaseManager(DB_URL, DB_USERNAME, DB_PASSWORD);
                dbManager.setCollectionManager(collectionManager);

                collectionManager.setDatabaseManager(dbManager);
                UserManager userManager = new UserManager(dbManager);
                collectionManager.setUserManager(userManager);
                clientConsole.setUserManager(userManager);
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
