import commands.ExecuteScript;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.consoles.StandartConsole;
import utility.interfaces.Console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static int PORT = 2223;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader inFromClient;
    private static BufferedWriter outToClient;
    //    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static Invoker invoker;
    private static CommandManager commandManager;
    private static String file;

    public Server(Invoker invoker, CommandManager commandManager) {
        Server.invoker = invoker;
        Server.commandManager = commandManager;
    }

    public static void setCommandManager(CommandManager commandManager) {
        Server.commandManager = commandManager;
    }

    public static void setInvoker(Invoker invoker) {
        Server.invoker = invoker;
    }

    public static void main(String[] args) throws IOException {
        Console console = new StandartConsole();

        FileManager fileManager = new FileManager();
        fileManager.setFile(getFile());

        CollectionManager collectionManager = new CollectionManager(fileManager);

        fileManager.setCollectionManager(collectionManager);

        Invoker invoker = new Invoker(null, console);

        CommandManager commandManager = new CommandManager(console, collectionManager);
        commandManager.addCommand("execute_script", new ExecuteScript(console, invoker));

        invoker.setCommandManager(commandManager);
        console.setCommandManager(commandManager);
        console.setInvoker(invoker);

        console.setCollectionManager(collectionManager);
        console.setScanner(new Scanner(System.in));

        collectionManager.setConsole(console);
        run();
    }


    public static void run() {
//        logger.info("Server has started on port: {}", PORT);
        try {
            while (true) {
                serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
//                logger.info("Client has connected!");
                break;
            }
            getFile();
            responseClient();
        } catch (IOException e) {
            System.out.println("Server_Даун1");
        } finally {
            try {
                inFromClient.close();
                outToClient.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getFile() throws IOException {
        inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        file = inFromClient.readLine();
        return file;
    }

    private static void responseClient() throws IOException {
        while (true) {
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String message = inFromClient.readLine();
            ExecutionResponse executionResponse;
            String[] command = (message + " ").split(" ", 2);
            command[0] = command[0].toLowerCase().trim();
            command[1] = command[1].toLowerCase().trim();
            if (!commandManager.getCommandsMap().containsKey(command[0])) {
                executionResponse = new ExecutionResponse(false, "Команда \"" + command[0] + "\" не найдена!\nНаберите \"help\" для справки!");
            } else {
                executionResponse = invoker.execute(command);
            }

            outToClient.write(executionResponse.getMessage());
            outToClient.newLine();
            outToClient.flush();
        }
    }
}
