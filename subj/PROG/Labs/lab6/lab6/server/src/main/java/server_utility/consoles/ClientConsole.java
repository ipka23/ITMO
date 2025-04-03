package server_utility.consoles;

import common_utility.exceptions.ExitException;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_utility.Invoker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

//Invoker, CollectionManager
public class ClientConsole extends StandartConsole {
    protected Scanner scanner;// = new Scanner(System.in);
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    protected Invoker invoker;
    protected CollectionManager collectionManager;
    protected CommandManager commandManager;
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;

    public ClientConsole(Invoker invoker, CollectionManager collectionManager, CommandManager commandManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        ClientConsole.inFromClient = inFromClient;
        ClientConsole.outToClient = outToClient;

        setScanner(new Scanner(System.in));
    }



    @Override
    public String nextLine() {
        Request request;
        try {
            request = (Request) inFromClient.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return request.getMessage();
    }




    @Override
    public void print(Object o) {
        try {
            outToClient.writeObject(o);
            outToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printPrompt() {
        try {
            outToClient.writeObject(PROMPT);
            outToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void launch() {
        try {
//            collectionManager.chooseTypeOfCollection();
            while (true) {
                printPrompt();
                String input = nextLine();
                if (input.trim().isEmpty()) continue;
                String[] command = (input + " ").split(" ", 2);
                command[0] = command[0].toLowerCase().trim();
                command[1] = command[1].toLowerCase().trim();
                Response commandStatus = invoker.execute(command);
                print(commandStatus);
            }
        } catch (ExitException e) {
            print(e);
        }
    }

}
