package fx.controllers;

import common_utility.database.User;
import common_utility.network.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import network.RequestSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
//    private RequestSender requestSender;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    public SceneController(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        this.outToServer = outToServer;
        this.inFromServer = inFromServer;
    }
    @FXML
    private TextField usernameLogIn;
    @FXML
    private PasswordField logInPassword;
    @FXML
    private TextField usernameRegister;
    @FXML
    private PasswordField registerPassword_1;
    @FXML
    private PasswordField registerPassword_2;

    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = usernameLogIn.getText();
        String password = logInPassword.getText();
        RequestSender.sendRequest(new Request("login", new User(username, password)), outToServer);
        root = FXMLLoader.load(getClass().getResource("/fx/login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent event) throws IOException {
        String username = usernameRegister.getText();
        String password_1 = registerPassword_1.getText();
        String password_2 = registerPassword_2.getText();
        root = FXMLLoader.load(getClass().getResource("/fx/register.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
