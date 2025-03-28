import commands.ExecuteScript;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.consoles.StandartConsole;
import utility.interfaces.Console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 1123;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader inFromClient;
    private static ObjectOutputStream outToClient;
    private static final Logger logger = LoggerFactory.getLogger(Server1.class);
    private static String file;

    private static Invoker invoker;
    private static Console console;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;

    public static void main(String[] args) throws IOException {
        console = new StandartConsole();
        fileManager = new FileManager(null, console, "MusicBands.json");
        collectionManager = new CollectionManager(fileManager, console);
        commandManager = new CommandManager(console, collectionManager);
        invoker = new Invoker(commandManager, console);

        fileManager.setCollectionManager(collectionManager);
        console.setCollectionManager(collectionManager);
        console.setInvoker(invoker);

        commandManager.addCommand("execute_script", new ExecuteScript(console, invoker));

//        console.launch();
        run();
    }
    public static void run() throws IOException {
//        fileManager.setFile(getFile());
        logger.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                logger.info("Client has connected!");
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outToClient = new ObjectOutputStream(socket.getOutputStream());
                break;
            }
            file = getFile();
            responseClient();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Server_Даун1");
        }
    }

    private static String getFile() throws IOException {
        file = inFromClient.readLine();
        logger.info("File received: {}", file);
        outToClient.writeObject("Файл получен на сервер успешно!");
        outToClient.flush();
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
            String responseMessage = executionResponse.getMessage();
            outToClient.writeObject(responseMessage.substring(0, responseMessage.length() - 1));
            outToClient.flush();
        }
    }
}
