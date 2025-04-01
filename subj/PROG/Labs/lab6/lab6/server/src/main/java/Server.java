import common_utility.network.Request;
import common_utility.network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_commands.Add;
import server_commands.ExecuteScript;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_managers.FileManager;
import server_utility.Invoker;
import server_utility.consoles.StandartConsole;
import server_utility.interfaces.Console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 1123;
    private static ServerSocket serverSocket;
    private static Socket socket;
//    private static Scanner scanner = new Scanner(System.in);
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static String collectionFileName;

    private static Invoker invoker;
    private static Console console;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;

    public static void main(String[] args) {
        console = new StandartConsole();
        fileManager = new FileManager(null, console, null);
        collectionManager = new CollectionManager(fileManager, console);
        commandManager = new CommandManager(console, collectionManager);
        invoker = new Invoker(commandManager, console);

        fileManager.setCollectionManager(collectionManager);
        console.setCollectionManager(collectionManager);
        console.setInvoker(invoker);

        commandManager.addCommand("execute_script", new ExecuteScript(console, invoker));


        run();
    }
    public static void run() {
        logger.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                logger.info("Client has connected!");
                inFromClient = new ObjectInputStream(socket.getInputStream());
                outToClient = new ObjectOutputStream(socket.getOutputStream());
                fileManager.setFile(getFile());
                commandManager.addCommand("add", new Add(console, collectionManager, inFromClient, outToClient));
                break;
            }

            responseClient();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getFile() throws IOException, ClassNotFoundException {
        StringBuilder f = new StringBuilder();
        for (int i = 0; i < inFromClient.available() ; i++) {
            f.append(inFromClient.readChar());
        }
        collectionFileName = f.toString();
        Response response;
        try {
            if (collectionFileName.isEmpty()) {
                response = new Response(true, "Введите имя файла как аргумент командной строки!");
            } else if (!new File(collectionFileName).exists()) {
                response = new Response(true, "Файл \"" + collectionFileName + "\" не найден!!!!");
            } else if (!new File(collectionFileName).canRead()) {
                response = new Response(true, "Нет прав на чтение файла \"" + collectionFileName + "\"!");
            } else {
                response = new Response(false, "Файл получен");
                logger.info("File received: {}", collectionFileName);
            }
            outToClient.writeObject(response);
            outToClient.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return collectionFileName;
    }

    private static void responseClient() throws IOException, ClassNotFoundException {
        while (true) {
            Request request = (Request) inFromClient.readObject();
            Response executionResponse;
            String[] command = (request.getMessage() + " ").split(" ", 2);
            command[0] = command[0].toLowerCase().trim();
            command[1] = command[1].toLowerCase().trim();
            if (!commandManager.getCommandsMap().containsKey(command[0])) {
                executionResponse = new Response(false, "Команда \"" + command[0] + "\" не найдена! Наберите \"help\" для справки!");
            } else {
                executionResponse = invoker.execute(command);
            }
            logger.info("Response: {}", executionResponse);
            Response response = executionResponse;
            outToClient.writeObject(response);
            outToClient.flush();
        }
    }
}
