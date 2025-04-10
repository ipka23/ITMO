package server_utility.consoles;

import common_utility.exceptions.ExitException;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Getter;
import lombok.Setter;
import server_commands.ExecuteScript;
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
    @Setter
    private boolean scriptMode = false;
    @Getter
    private StringBuilder tmp;

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


    public void send(Object o) {
        if (!scriptMode) {
            try {
                outToClient.writeObject(o);
                outToClient.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            tmp = new StringBuilder();
            tmp.append(o.toString());   //TODO
        }
    }


    public Request getRequest() throws IOException, ClassNotFoundException {
        if (!scriptMode) {
            return (Request) inFromClient.readObject();
        } else {
            ExecuteScript executeScript = new ExecuteScript(this, invoker, inFromClient, outToClient);
            return null; //TODO
        }
    }

    public void sendPrompt() {
        try {
            outToClient.writeObject(new Response(false, PROMPT));
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
                sendPrompt();
                Request request = (Request) inFromClient.readObject();
                String command = request.getCommand();
                String arg = request.getArg();
                if (command.isEmpty()) continue;
                Response response = invoker.execute(new String[]{command, arg});

                send(response);
            }
        } catch (ExitException e) {
            print(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
