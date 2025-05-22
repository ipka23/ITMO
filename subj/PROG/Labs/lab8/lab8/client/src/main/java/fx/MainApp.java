package fx;

import fx.controllers.SceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import network.Client;
import network.RequestSender;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainApp extends Application {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private volatile SceneController sceneController;
    private RequestSender rs;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/auth.fxml"));
            Parent root = loader.load();
            sceneController = loader.getController();
            stage.setResizable(false);
            Image icon = new Image("images/pokeball.png");
            stage.getIcons().add(icon);
            stage.setTitle("MusicBands");
            sceneController.setLanguageBox();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        executor.submit(() -> {
            try {
                int port = 1232;
                String hostName = "localhost";
                Socket socket;
                socket = new Socket(hostName, port);
                Client client = new Client(port, hostName, socket);

                ObjectOutputStream outToServer = new ObjectOutputStream(new BufferedOutputStream(client.getSocket().getOutputStream()));
                outToServer.flush();

                ObjectInputStream inFromServer = new ObjectInputStream(new BufferedInputStream(client.getSocket().getInputStream()));
                rs = new RequestSender(outToServer, inFromServer);
                Platform.runLater(() -> sceneController.setRequestSender(rs));


//                requestSender.authentication(outToServer, inFromServer);
//                requestSender.sendMessage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
