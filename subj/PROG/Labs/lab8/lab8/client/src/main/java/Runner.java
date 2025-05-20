import fx.controllers.SceneController;
import network.Client;
import network.RequestSender;

import java.io.*;

public class Runner {

    public static void run(Client client) throws IOException, ClassNotFoundException {
        try {
            ObjectOutputStream outToServer = new ObjectOutputStream(new BufferedOutputStream(client.getSocket().getOutputStream()));
            outToServer.flush();

            ObjectInputStream inFromServer = new ObjectInputStream(new BufferedInputStream(client.getSocket().getInputStream()));
            RequestSender requestSender = new RequestSender(outToServer, inFromServer);
            SceneController sceneController = new SceneController();
            sceneController.setRequestSender(requestSender);


            requestSender.authentication(outToServer, inFromServer);
            requestSender.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
