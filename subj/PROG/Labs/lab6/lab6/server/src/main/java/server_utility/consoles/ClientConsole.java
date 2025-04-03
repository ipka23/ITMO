package server_utility.consoles;

import common_utility.exceptions.ExitException;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_utility.Invoker;
import server_utility.interfaces.Networkable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

//Invoker, CollectionManager
public class ClientConsole extends StandartConsole implements Networkable {
    protected Scanner scanner;// = new Scanner(System.in);
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    protected Invoker invoker;
    protected CollectionManager collectionManager;
    protected CommandManager commandManager;
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;
    private String responseToClient;

    public ClientConsole(Invoker invoker, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
        ClientConsole.inFromClient = inFromClient;
        ClientConsole.outToClient = outToClient;

        setScanner(new Scanner(System.in));
    }

    public ClientConsole(Invoker invoker) {
        this.invoker = invoker;
    }

    public ClientConsole() {}


    @Override
    public void setObjectInputStream(ObjectInputStream in) {
        ClientConsole.inFromClient = in;
    }

    @Override
    public void setObjectOutputStream(ObjectOutputStream out) {
        ClientConsole.outToClient = out;
    }


    public String getClientMessage() {
        Request request;
        try {
            request = (Request) inFromClient.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return request.getMessage();
    }




    public void write(Object o) {
        try {
            outToClient.writeObject(o);
            outToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writePrompt() {
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
                writePrompt();
                Request request = (Request) inFromClient.readObject();
                String input = request.getMessage();
                if (input.trim().isEmpty()) continue;
                String[] command = (input + " ").split(" ", 2);
                command[0] = command[0].toLowerCase().trim();
                command[1] = command[1].toLowerCase().trim();
                Response response = invoker.execute(command);

                write(response);
            }
        } catch (ExitException e) {
            print(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
