package server_utility.consoles;

import common_utility.database.User;
import common_utility.exceptions.ExitException;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Getter;
import lombok.Setter;
import server_managers.CollectionManager;
import server_managers.UserManager;
import server_utility.Invoker;
import server_utility.interfaces.ObjectStreamsWorkable;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Invoker, CollectionManager
public class ClientConsole extends StandartConsole implements ObjectStreamsWorkable {
    protected Scanner scanner;// = new Scanner(System.in);
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    protected Invoker invoker;
    @Getter
    protected CollectionManager collectionManager;
    /*private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;*/
    @Setter
    @Getter
    private boolean scriptMode = false;
    @Getter
    private StringBuilder tmp;
    @Setter
    @Getter
    private File scriptFile;
    @Getter
    @Setter
    private UserManager userManager;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final ExecutorService cachedThreadPull = Executors.newCachedThreadPool();

    public ClientConsole(Invoker invoker, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
//        ClientConsole.inFromClient = inFromClient;
//        ClientConsole.outToClient = outToClient;

        setScanner(new Scanner(System.in));
    }

    public ClientConsole(Invoker invoker) {
        this.invoker = invoker;
    }


    @Override
    public void setObjectInputStream(ObjectInputStream in) {
//        ClientConsole.inFromClient = in;
    }

    @Override
    public void setObjectOutputStream(ObjectOutputStream out) {
//        ClientConsole.outToClient = out;
    }


    public void sendResponse(Object o, ObjectOutputStream outToClient) {
        executor.submit(() -> { //todo
            synchronized (outToClient) {
                try {
                    outToClient.writeObject(o);
                    outToClient.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public Request getRequest(ObjectInputStream inFromClient) throws IOException, ClassNotFoundException {
        if (!scriptMode) {
            synchronized (inFromClient) {
                return (Request) inFromClient.readObject();
            }
        } else {
            synchronized (inFromClient) {
                String currentLine;
                try (Scanner fileScanner = new Scanner(new FileReader(scriptFile))) {
                    currentLine = fileScanner.nextLine();
                }
                return new Request(currentLine);
            }
        }
    }

    public void sendPrompt(ObjectOutputStream outToClient) {
        executor.submit(() -> { //todo
            synchronized (outToClient) {
                try {
                    outToClient.writeObject(new Response(false, PROMPT));
                    outToClient.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }





    public void run(ObjectInputStream inFromClient, ObjectOutputStream outToClient) {

        try {
//            outToClient.flush();
            authentication(outToClient, inFromClient);
            while (true) {
                sendPrompt(outToClient);
                Request request;
                request = getRequest(inFromClient);
                String command = (request.getMessage() + " ").split(" ", 2)[0];
                String arg = (request.getMessage() + " ").split(" ", 2)[1];
                if (command.isEmpty()) continue;
                Response response = invoker.execute(new String[]{command, arg});
                sendResponse(response, outToClient);
            }
        } catch (ExitException e) {
            print(e);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void authentication(ObjectOutputStream outToClient, ObjectInputStream inFromClient) throws IOException, ClassNotFoundException, SQLException {

        Request request = getRequest(inFromClient);
        User user = request.getUser();
        String message = request.getMessage();
        if (message.equals("login")) {
            if (userManager.logInUser(user)) {
                sendResponse(new Response(true), outToClient);
//                synchronized (outToClient) {outToClient.writeObject(new Response(true));}
            } else
                sendResponse(new Response(false), outToClient);
//                synchronized (outToClient) {outToClient.writeObject(new Response(false));}
        } else if (message.equals("register")) {
            if (userManager.registerUser(user)) {
                sendResponse(new Response(true), outToClient);
//                synchronized (outToClient) {outToClient.writeObject(new Response(true));}
            } else
                sendResponse(new Response(false, "Непредвиденная ошибка!"), outToClient);
//            synchronized (outToClient) {outToClient.writeObject(new Response(false, "Непредвиденная ошибка!"));}

        }
    }

}