package server_utility.consoles;

import common_entities.MusicBand;
import common_utility.database.User;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import server_managers.CollectionManager;
import server_managers.UserManager;
import server_utility.Invoker;
import server_utility.Server;
import server_utility.multithreading.Refresher;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Invoker, CollectionManager
@Slf4j
public class ClientConsole {
    private final String PROMPT = ">";
    protected Invoker invoker;
    @Setter
    @Getter
    private boolean scriptMode = false;
    @Setter
    @Getter
    private File scriptFile;
    @Setter
    @Getter
    private UserManager userManager;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    @Getter
    private StringBuilder tmp;

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
//                sendPrompt(outToClient);
                Request request;
                request = getRequest(inFromClient);

//                log.info(request.toString());

                String command = (request.getMessage() + " ").split(" ", 2)[0];
                String arg = (request.getMessage() + " ").split(" ", 2)[1];
                if (command.isEmpty()) continue;
                /*if (command.equals("logout")){
                todo
                }*/
                if (command.startsWith("add") || command.equals("clear") || command.equals("remove_greater") || command.equals("update") || command.equals("remove")) {
                    Response response = invoker.execute(new String[]{command, arg}, request);
                    sendResponse(response, outToClient);
                }
                /*if (command.equals("refresh")) {
//                    sendResponse(new Response(true, "refresh", collectionManager.getCollection()), outToClient);
                    collectionManager.getDatabaseManager().loadCollectionFromDB();
                    sendResponse(new Response(true,"refresh", collectionManager.getCollection()), outToClient);
//                    Refresher.refresh(collectionManager.getCollection());
                }*/
                else {
                    Response response = invoker.execute(new String[]{command, arg});
                    sendResponse(response, outToClient);
                }
//                Refresher.refresh(userManager.getCollectionManager().getCollection());

            }
        } catch (IOException | ClassNotFoundException e) {
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