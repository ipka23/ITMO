import common_utility.ExecutionResponse;
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
    private static BufferedReader inFromClient;
    private static ObjectOutputStream outToClient;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static String file;

    private static Invoker invoker;
    private static Console console;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;

    public static void main(String[] args) throws IOException {
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
    public static void run() throws IOException {
        logger.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                logger.info("Client has connected!");
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

    private static String getFile() throws IOException {
        file = inFromClient.readLine().trim();
        ExecutionResponse response;
        try {
            if (file.isEmpty()) {
                response = new ExecutionResponse(false, "Введите имя файла как аргумент командной строки!");
            } else if (!new File(file).exists()) {
                response = new ExecutionResponse(false, "Файл \"" + file + "\" не найден!!!!");
            } else if (!new File(file).canRead()) {
                response = new ExecutionResponse(false, "Нет прав на чтение файла \"" + file + "\"!");
            } else {
                response = new ExecutionResponse(false);
                logger.info("File received: {}", file);
            }
            outToClient.writeObject(response);
            outToClient.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return file;
    }

    private static void responseClient() throws IOException, ClassNotFoundException {
        while (true) {
            String message = inFromClient.readLine();
            ExecutionResponse executionResponse;
            String[] command = (message + " ").split(" ", 2);
            command[0] = command[0].toLowerCase().trim();
            command[1] = command[1].toLowerCase().trim();
            if (!commandManager.getCommandsMap().containsKey(command[0])) {
                executionResponse = new ExecutionResponse(false, "Команда \"" + command[0] + "\" не найдена! Наберите \"help\" для справки!");
            } else {
                executionResponse = invoker.execute(command);
            }
            logger.info("Response: {}", executionResponse);
            ExecutionResponse response = executionResponse;
            outToClient.writeObject(response);
            outToClient.flush();
        }
    }
}
