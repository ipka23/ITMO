package mainpac;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cli implements Runnable {
    public static int PORT = 1234;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static Scanner userInput;
    private static BufferedReader inFromServer;
    private static BufferedWriter outToServer;

    public static void main(String[] args) {
        Cli cli = new Cli();
        cli.run();
    }

    @Override
    public void run() {
        System.out.println("App started!");

        try {
            declare();
            while (true) {
                sendMessage();
            }
        } catch (IOException e) {
            System.out.println("Client_Даун1");
        } finally {
            try {
                userInput.close();
                inFromServer.close();
                outToServer.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void sendMessage() throws IOException {
        try {
            outToServer.write(input());
            outToServer.newLine();
            outToServer.flush();

            String response = inFromServer.readLine();
            System.out.println(response);
        } catch (ExitTestException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Client_Даун2");
        }
    }


    private static void declare() throws IOException {
        socket = new Socket("localhost", PORT);
        userInput = new Scanner(System.in);
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }


    public static String input() {
        while (true) {
            System.out.print("$ ");
            String line = userInput.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.equals("exit")) {
                System.exit(0);
                throw new ExitTestException();
            }
            return line;
        }
    }
}
