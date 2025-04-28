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
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;
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
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final ExecutorService cachedThreadPull = Executors.newCachedThreadPool();

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



    public void send(Object o) {
        executor.submit(() -> { //todo
            try {
                outToClient.writeObject(o);
                outToClient.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Request read() throws IOException, ClassNotFoundException {
        return (Request) inFromClient.readObject();
    }

    public void sendPrompt() {
        executor.submit(() -> { //todo
            try {
                outToClient.writeObject(new Response(false, PROMPT));
                outToClient.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }



    public Request getRequest() throws IOException, ClassNotFoundException {
        if (!scriptMode) {
            return (Request) inFromClient.readObject();
        } else {
            String currentLine;
            try (Scanner fileScanner = new Scanner(new FileReader(scriptFile))) {
                currentLine = fileScanner.nextLine();
            }
            return new Request(currentLine);
        }
    }


    @Override
    public void launch() {
        try {
            /*cachedThreadPull.submit(() -> {
                try {*/
            authentication();
                /*} catch (IOException | ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            });*/
            while (true) {
                sendPrompt();
                Request request = read();
                String command = (request.getMessage() + " ").split(" ", 2)[0];
                String arg = (request.getMessage() + " ").split(" ", 2)[1];

                if (command.isEmpty()) continue;
                Response response = invoker.execute(new String[]{command, arg});

                send(response);
            }
        } catch (ExitException e) {
            print(e);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void authentication() throws IOException, ClassNotFoundException, SQLException {

        Request request = read();
        User user = request.getUser();
        String message = request.getMessage();
        if (message.equals("login")) {
            if (userManager.logInUser(user)) {
                /*send(new Response(true));*/ outToClient.writeObject(new Response(true));
            } else /*send(new Response(false));*/ outToClient.writeObject(new Response(false));
        } else if (message.equals("register")) {
            if (userManager.registerUser(user)) {
                /*send(new Response(true));*/ outToClient.writeObject(new Response(true));
            } else /*send(new Response(false, "Непредвиденная ошибка!"));*/ outToClient.writeObject(new Response(false, "Непредвиденная ошибка!"));
        }
    }

}