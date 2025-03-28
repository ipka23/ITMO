import utility.exceptions.ExitException;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static int PORT = 2223;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static Scanner userInput;
    private static BufferedReader inFromServer;
    private static BufferedWriter outToServer;
    private static String file;


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Введите имя файла как аргумент командной строки!");
            System.exit(2);
        } else if (!new File(args[0]).exists()) {
            file = args[0];
            System.out.print("Файл \"" + file + "\" не найден!");
            System.exit(2);
        } else if (!new File(args[0]).canRead()) {
            file = args[0];
            System.out.print("Нет прав на чтение файла \"" + file + "\"!");
            System.exit(2);
        }
        file = args[0];


        run();
    }

    public static void run() {
        System.out.println("App started!");

        try {
            socket = new Socket("localhost", PORT);
            userInput = new Scanner(System.in);

            sendMessage();
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

    private void sendFile(String file) throws IOException {
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        while (true) {
            System.out.print("Введите название файла: ");
            userInput = new Scanner(System.in);
            if (userInput.nextLine().trim().matches("^\\b.*\\.txt$")) {
                Client.file = userInput.nextLine().trim();
                outToServer.write(file);
                outToServer.flush();
                break;
            } else {
                System.out.println("Введите название txt файла (example_file.txt)!");
            }
        }




    }

    private static void sendMessage() throws IOException {
        try {

            while (true) {
                inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                outToServer.write(input());
                outToServer.newLine();
                outToServer.flush();
                String response = inFromServer.readLine();
                System.out.println(response);
            }
        } catch (ExitException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Client_Даун2");
        }
    }





    public static String input() {
        while (true) {
            System.out.print("$ ");
            String line = userInput.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.equals("exit")) {
                System.exit(0);
                throw new ExitException();
            }
            return line;
        }
    }
}
