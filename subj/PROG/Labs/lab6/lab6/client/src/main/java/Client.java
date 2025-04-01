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
    private static Socket socket;
    private static Scanner userInput;
    private static ObjectInputStream inFromServer;
    private static ObjectOutputStream outToServer;
    private static String file;
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        if (args.length == 0) {
            System.exit(1);
        } else {
            file = args[0];
        }
        run();


    }

    public static void run() throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", PORT);
        userInput = new Scanner(System.in);
        inFromServer = new ObjectInputStream(socket.getInputStream());
        outToServer = new ObjectOutputStream(socket.getOutputStream());
        sendFile(file);
        sendMessage();
    }

    private static void sendFile(String file) throws IOException, ClassNotFoundException {
        outToServer.writeChars(file);
        outToServer.flush();
        Response response = (Response) inFromServer.readObject();
        if (response.getExitStatus()) {
            System.out.print(response);
            System.exit(222);
        }
        System.out.println(response);

    }

    private static void sendMessage() {
        try {
            while (true) {
                String message = input();
                Request request = new Request(message);

//                if (message.equals("add")) {
//                    outToServer.write(message);
//                    outToServer.newLine();
//                    outToServer.flush();
//                    printOutFromServer = inFromServer;
//                }
                outToServer.writeObject(request);
                outToServer.flush();
                Response response = (Response) inFromServer.readObject();

                if (response.getExitStatus()) {
                    System.out.print(response);
                    System.exit(333);
                } else {
                    System.out.println(response);
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public static String input() {
        while (true) {
            System.out.print("$ ");
            String line = userInput.nextLine().trim();
            if (line.isEmpty()) continue;
            return line;
        }
    }
}
