import common_utility.network.ExecutionResponse;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static int PORT = 1123;
    private static Socket socket;
    private static Scanner userInput;
    private static ObjectInputStream inFromServer;
    private static PrintStream printOutFromServer;
    private static BufferedWriter outToServer;
    private static String file;


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
        outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        sendFile(file);
        sendMessage();
    }

    private static void sendFile(String file) throws IOException, ClassNotFoundException {
        outToServer.write(file);
        outToServer.newLine();
        outToServer.flush();
        ExecutionResponse response = (ExecutionResponse) inFromServer.readObject();
        if (response.getExitStatus()) {
            System.out.print(response);
            System.exit(222);
        }
    }

    private static void sendMessage() throws IOException, ClassNotFoundException {
        try {
            while (true) {
                String message = input();
//                if (message.equals("add")) {
//                    outToServer.write(message);
//                    outToServer.newLine();
//                    outToServer.flush();
//                    printOutFromServer = inFromServer;
//                }
                outToServer.write(message);
                outToServer.newLine();
                outToServer.flush();
                ExecutionResponse response = (ExecutionResponse) inFromServer.readObject();

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
