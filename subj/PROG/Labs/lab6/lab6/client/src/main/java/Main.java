import network.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static int port = 1123;
    private static String hostName = "localhost";
    private static Socket socket;
    private static Scanner userInput;
    private static ObjectInputStream inFromServer;
    private static ObjectOutputStream outToServer;
    private static String collectionFile;

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.exit(1);
        } else {
            collectionFile = args[0];
        }
        socket = new Socket(hostName, port);
        Client client = new Client(port, hostName, socket, collectionFile);
        try {
            Runner.run(client);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            socket.close();
            inFromServer.close();
            outToServer.close();
        }
    }
}
