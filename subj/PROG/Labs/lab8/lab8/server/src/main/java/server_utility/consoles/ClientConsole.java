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

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Invoker, CollectionManager
public class ClientConsole extends StandartConsole {
    private final String PROMPT = ">";
    protected Invoker invoker;
    @Getter
    protected CollectionManager collectionManager;
    @Setter @Getter
    private boolean scriptMode = false;
    @Setter @Getter
    private File scriptFile;
    @Setter @Getter
    private UserManager userManager;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    @Getter
    private StringBuilder tmp;

    public ClientConsole(Invoker invoker, CollectionManager collectionManager) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
        setScanner(new Scanner(System.in));
    }

    public ClientConsole(Invoker invoker) {
        this.invoker = invoker;
    }


    public void write(String text) {
        tmp = new StringBuilder();
        tmp.append(text);
    }
    public void sendResponse(Object o, ObjectOutputStream outToClient) {
        executor.submit(() -> {
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
        executor.submit(() -> {
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
            authentication(outToClient, inFromClient);
            while (true) {
                sendPrompt(outToClient);
                Request request;
                request = getRequest(inFromClient);
                String command = (request.getMessage() + " ").split(" ", 2)[0];
                String arg = (request.getMessage() + " ").split(" ", 2)[1];
                if (command.isEmpty()) continue;
                /*if (command.equals("logout")){
                todo
                }*/
                Response response = invoker.execute(new String[]{command, arg});
                sendResponse(response, outToClient);
            }
        } catch (ExitException e) {
            print(e);
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void authentication(ObjectOutputStream outToClient, ObjectInputStream inFromClient) throws IOException, ClassNotFoundException {
            Request request = getRequest(inFromClient);
            User user = request.getUser();
            String message = request.getMessage();
            Response response;
            if (message.equals("login")) {
                while (true) {
                    response = userManager.logInUser(user);
                    if (response.getExitStatus()) {
                        sendResponse(response, outToClient);
                        break;
                    } else {
                        sendResponse(response, outToClient);
                        request = getRequest(inFromClient);
                        user = request.getUser();
                    }
                }

            } else if (message.equals("register")) {
                while (true) {
                    response = userManager.registerUser(user);
                    if (response.getExitStatus()) {
                        sendResponse(response, outToClient);
                        break;
                    } else {
                        sendResponse(response, outToClient);
                        request = getRequest(inFromClient);
                        user = request.getUser();
                    }

                }
            }
    }

}