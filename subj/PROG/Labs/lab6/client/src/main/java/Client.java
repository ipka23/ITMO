import utility.exceptions.ExitException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static int PORT = 1123;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static Scanner userInput;
    private static ObjectInputStream inFromServer;
    private static BufferedWriter outToServer;
    private static String file;


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        if (args.length == 0) {
            System.exit(1);
        } else {
            file = args[0];
        }
        run();
//        sendFile(file);


    }

    public static void run() throws IOException, ClassNotFoundException {
//        try {
        socket = new Socket("localhost", PORT);
        userInput = new Scanner(System.in);
        inFromServer = new ObjectInputStream(socket.getInputStream());
        outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        sendFile(file);
        sendMessage();
//        } catch (IOException e) {
////            System.out.println("Client_error_1");
//        }
    }

    private static void sendFile(String file) throws IOException, ClassNotFoundException {
        outToServer.write(file);
        outToServer.newLine();
        outToServer.flush();
        ExecutionResponse response = (ExecutionResponse) inFromServer.readObject();
        if (response.getExecutionResult()) {
            System.out.print(response.getMessage());
            System.exit(322);
        } else {
            System.out.println(response.getMessage());
        }
    }

    private static void sendMessage() throws IOException {
        try {
            while (true) {
                outToServer.write(input());
                outToServer.newLine();
                outToServer.flush();
                ExecutionResponse response = (ExecutionResponse) inFromServer.readObject();

                if (response.getExecutionResult()) {
                    System.out.print(response.getMessage());
                    System.exit(1);
                } else {
                    System.out.println(response.getMessage());
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
            if (line.equals("exit")) {
                throw new ExitException();
            }
            return line;
        }
    }
}
