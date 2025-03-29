import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.Command;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.consoles.StandartConsole;
import utility.interfaces.Console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server1 {
    public static int PORT = 2223;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader inFromClient;
    private static BufferedWriter outToClient;
    private static final Logger logger = LoggerFactory.getLogger(Server1.class);

    private static Invoker invoker;
    private static Console console;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;


    private static String file;
    private static Map<String, Command> commandsMap = new HashMap<>();





    public static void setCommandManager(CommandManager commandManager) {
        Server1.commandManager = commandManager;
    }

    public static void setInvoker(Invoker invoker) {
        Server1.invoker = invoker;
    }

    public static void main(String[] args) throws IOException {

        run();
    }

    private static void declareCommandManager() throws IOException {
        logger.info("Declare command manager");
        console = new StandartConsole();
        fileManager = new FileManager();
        collectionManager = new CollectionManager(fileManager);
        commandManager = new CommandManager(console, collectionManager);
        declareCommands();
        commandsMap = commandManager.getCommandsMap();
    }
    private static void declareServerParams() throws IOException {
        declareCommandManager();
        logger.info("Declare server parameters");



        fileManager.setCollectionManager(collectionManager);
        fileManager.setConsole(console);
        fileManager.loadCollectionFromFile();
        invoker = new Invoker(null, console);


        commandManager.addCommand("execute_script", new ExecuteScript(console, invoker));


        invoker.setCommandManager(commandManager);
        console.setCommandManager(commandManager);
        console.setInvoker(invoker);

        console.setCollectionManager(collectionManager);
        console.setScanner(new Scanner(System.in));

        collectionManager.setConsole(console);

    }


    public static void run() throws IOException {
        declareServerParams();
        logger.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                logger.info("Client has connected!");
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                break;
            }
            file = getFile();
            responseClient();
        } catch (IOException e) {
            System.out.println("Server_Даун1");
        }
    }

    private static String getFile() throws IOException {
        file = inFromClient.readLine();
        logger.info("File received: {}", file);
        outToClient.write("Файл получен на сервер успешно!");
        outToClient.newLine();
        outToClient.flush();
        return file;
    }

    private static void responseClient() throws IOException {
        while (true) {

            String message = inFromClient.readLine();
            ExecutionResponse executionResponse;
            String[] command = (message + " ").split(" ", 2);
            command[0] = command[0].toLowerCase().trim();
            command[1] = command[1].toLowerCase().trim();
            if (!commandsMap.containsKey(command[0])) {
                executionResponse = new ExecutionResponse(false, "Команда \"" + command[0] + "\" не найдена! Наберите \"help\" для справки!");
            } else {
                executionResponse = invoker.execute(command);
            }
            logger.info("Response: {}", executionResponse);

            outToClient.write(executionResponse.getMessage());
            outToClient.newLine();
            outToClient.flush();
        }
    }
    private static void declareCommands() {
        logger.info("Declare commands");
        commandsMap.put("add", new Add(console, collectionManager));
        commandsMap.put("add_if_max", new AddIfMax(console, collectionManager));
        commandsMap.put("add_if_min", new AddIfMin(console, collectionManager));
        commandsMap.put("clear", new Clear(console, collectionManager));
        commandsMap.put("exit", new Exit());
        commandsMap.put("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandsMap.put("help", new Help(console, commandManager));
        commandsMap.put("info", new Info(console, collectionManager));
        commandsMap.put("max_by_best_album", new MaxByBestBestAlbum(console, collectionManager));
        commandsMap.put("print_field_ascending_establishment_date", new PrintFieldAscendingEstablishmentDate(console, collectionManager));
        commandsMap.put("remove_by_id", new RemoveByID(console, collectionManager));
        commandsMap.put("remove_greater", new RemoveGreater(console, collectionManager));
        commandsMap.put("show", new Show(console, collectionManager));
        commandsMap.put("update", new Update(console, collectionManager));
    }
}