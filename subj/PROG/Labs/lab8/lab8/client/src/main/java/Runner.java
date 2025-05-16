import network.Client;
import network.RequestSender;

import java.io.*;
import java.util.Scanner;

public class Runner {

    public static void run(Client client) throws IOException, ClassNotFoundException {
        try {
            ObjectOutputStream outToServer = new ObjectOutputStream(new BufferedOutputStream(client.getSocket().getOutputStream()));
            outToServer.flush();

            ObjectInputStream inFromServer = new ObjectInputStream(new BufferedInputStream(client.getSocket().getInputStream()));

            Scanner userInput = new Scanner(System.in);

            RequestSender.sendMessage(outToServer, inFromServer, userInput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }
}
