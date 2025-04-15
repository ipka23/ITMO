import common_entities.MusicBand;
import common_utility.network.Request;
import common_utility.network.Response;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;


public class Client {
    public static int PORT = 1231;
    private static String hostName = "localhost";
    private static Socket socket;
    private static Scanner userInput;
    private static ObjectInputStream inFromServer;
    private static ObjectOutputStream outToServer;
    private static String collectionFile;
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();



    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.exit(1);
        } else {
            collectionFile = args[0];
        }

        try {
            run();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            socket.close();
            inFromServer.close();
            outToServer.close();
            userInput.close();
        }


    }

//    public static void sendRequest(Object o) throws IOException {
//        outToServer.writeObject(o);
//        outToServer.flush();
//    }

//    public static Response getResponse() throws IOException, ClassNotFoundException {
//        return (Response) inFromServer.readObject();
//    }

    public static void run() throws IOException, ClassNotFoundException {
        socket = new Socket(hostName, PORT);
        outToServer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        outToServer.flush();
        inFromServer = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        sendCollectionFile(collectionFile);
        userInput = new Scanner(System.in);
        sendMessage();
    }

    private static void sendCollectionFile(String file) throws IOException, ClassNotFoundException {
        outToServer.writeObject(new Request(file));
        outToServer.flush();

        Response response = (Response) inFromServer.readObject();
        if (response.getExitStatus()) {
            System.out.print(response.getMessage());
            System.exit(222);
        }

    }




    private static void sendMessage() {
        Response response;
        Request request;
        String command;
        String arg;
        try {
            while (true) {
                Response prompt = (Response) inFromServer.readObject();
                System.out.print(prompt.getMessage());
                String message = userInput.nextLine().trim();
                
                command = (message + " ").split(" ", 2)[0].trim().toLowerCase();
                arg = (message + " ").split(" ", 2)[1].trim();

                request = new Request(command, arg);


                outToServer.writeObject(request);
                outToServer.flush();

                if (command.equals("execute_script")) {
                    sendScriptFile(arg);
                }

                if (message.isEmpty()) continue;
                response = (Response) inFromServer.readObject();
                if (response == null) continue;
                else if (response.getExitStatus()) {
                    System.out.print(response);
                    System.exit(333);
                }
                if (command.equals("show") || command.equals("filter_starts_with_name")) { // response.getMusicBandsCollection() != null
                    show(response);
                }
                else {
                    System.out.println(response.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void show(Response response) {
        musicBandsCollection = response.getMusicBandsCollection();
        if (musicBandsCollection == null || musicBandsCollection.isEmpty()) {
            System.out.println(response.getMessage());
        }
        else {
            System.out.printf("|%-30s | %-30s | %-20s|%n", "Название группы", "Лучший альбом", "Количество продаж");
            System.out.println("_".repeat(88));
            for (var band : musicBandsCollection) {
                System.out.printf("|%-30s | %-30s | %-20.0f|%n", band.getName(), band.getBestAlbum().getName(), band.getBestAlbum().getSales());
            }
        }
    }
    private static void sendScriptFile(String path) throws FileNotFoundException {
        File scriptFile = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(scriptFile))) {
            StringBuilder scriptContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                scriptContent.append(line).append("\n");
            }
            scriptContent = new StringBuilder(scriptContent.substring(0, scriptContent.toString().length() - 1));
            Request request = new Request(scriptContent.toString());
            request.setFileName(path);
            request.setScriptFile(scriptFile);
            outToServer.writeObject(request);
            outToServer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}