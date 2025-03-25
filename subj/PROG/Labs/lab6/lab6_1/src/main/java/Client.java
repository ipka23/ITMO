import commands.ExecuteScript;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.Invoker;
import utility.consoles.AdvancedConsole;
import utility.consoles.StandartConsole;
import utility.exceptions.ExitException;
import utility.interfaces.Console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static int PORT = 2223;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static Scanner userInput;
    private static BufferedReader inFromServer;
    private static BufferedWriter outToServer;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Введите имя файла как аргумент командной строки!");
            System.exit(2);
        } else if (!new File(args[0]).exists()) {
            System.out.print("Файл \"" + args[0] + "\" не найден!");
            System.exit(2);
        } else if (!new File(args[0]).canRead()) {
            System.out.print("Нет прав на чтение файла \"" + args[0] + "\"!");
            System.exit(2);
        }

        Console console = new StandartConsole();

        FileManager fileManager = new FileManager();
        fileManager.setFile(args[0]);

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
        System.out.println("App started!");

        try {
            while (true) {
                declare();
                sendMessage();
            }
        } catch (IOException e) {
            System.out.println("Client_Даун1");
        } finally {
            try {
                userInput.close();
                inFromServer.close();
                outToServer.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void sendMessage() throws IOException {
        try {
            outToServer.write(input());
            outToServer.newLine();
            outToServer.flush();
            String response = inFromServer.readLine();
            System.out.println(response);
        } catch (ExitException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Client_Даун2");
        }
    }


    private static void declare() throws IOException {
        socket = new Socket("localhost", PORT);
        userInput = new Scanner(System.in);
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }


    public static String input() {
        while (true) {
            System.out.print("$ ");
            String line = userInput.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.equals("exit")) {
                System.exit(0);
                throw new ExitException();
            }
            return line;
        }
    }
}
