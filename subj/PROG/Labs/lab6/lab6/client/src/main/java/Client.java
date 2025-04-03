import common_entities.MusicBand;
import common_utility.network.Request;
import common_utility.network.Response;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class Client {
    public static int PORT = 1123;
    private static Socket socket = null;
    private static Scanner userInput;
    private static ObjectInputStream inFromServer = null;
    private static ObjectOutputStream outToServer = null;
    private static String file;
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();
    private static final String PROMPT = "$ ";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        if (args.length == 0) {
            System.exit(1);
        } else {
            file = args[0];
        }

        try {
            run();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            socket.close();
            inFromServer.close();
            outToServer.close();
            userInput.close();
        }


    }

    public static void run() throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", PORT);
        outToServer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        outToServer.flush();
        inFromServer = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        sendFile(file);
        userInput = new Scanner(System.in);
        sendMessage();
    }

    private static void sendFile(String file) throws IOException, ClassNotFoundException {
        outToServer.flush();
        outToServer.writeObject(new Request(file));
        outToServer.flush();
        Response response = (Response) inFromServer.readObject();
        if (response.getExitStatus()) {
            System.out.print(response);
            System.exit(222);
        }

    }

    private static void sendMessage() {
        try {
            while (true) {

//                System.out.print("$ ");
//                String message = userInput.nextLine().trim();
//                if (message.isEmpty()) {
//                    System.out.print("$ ");
//                    System.out.println(inFromServer.readObject());
//                    continue;
//                }

//                String message = input();
                System.out.print(PROMPT);
                String message = userInput.nextLine().trim();
                if (message.isEmpty()) continue;
                Request request = new Request(message);
//                message = userInput.nextLine().trim();


                outToServer.writeObject(request);
                outToServer.flush();
                Response response = (Response) inFromServer.readObject();
//                if (message.isEmpty()) {
//                    System.out.println(PROMPT);
//                    System.out.println(response);
//                    continue;
//                }

                if (response.getExitStatus()) {
                    System.out.print(response);
                    System.exit(333);
                }
                else {
                    System.out.println(response);
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



//    public static String input() {
//        return userInput.nextLine().trim();
//    }
}