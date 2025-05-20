package fx.controllers;

import common_utility.database.User;
import common_utility.network.Request;
import common_utility.network.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import network.RequestSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private RequestSender rs;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;

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
    private Label registerMessage;
    @FXML
    private Label logInMessage;


    public SceneController() {

    }

    public void setRequestSender(RequestSender rs) {
        this.rs = rs;
        this.outToServer = rs.getOutToServer();
        this.inFromServer = rs.getInFromServer();
    }
    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/login.fxml"));
        root = loader.load();
        SceneController controller = loader.getController();
        controller.setRequestSender(rs);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/register.fxml"));
        root = loader.load();
        SceneController controller = loader.getController();
        controller.setRequestSender(rs);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void backFromRegister(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/auth.fxml"));
        root = loader.load();
        SceneController controller = loader.getController();
        controller.setRequestSender(rs);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void backFromLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/auth.fxml"));
        root = loader.load();
        SceneController controller = loader.getController();
        controller.setRequestSender(rs);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void completeLogin(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {
        String username = usernameLogIn.getText();
        String password = logInPassword.getText();
        System.out.printf("%s %s", username, password);
        rs.sendRequest(new Request("login", new User(username, password)), outToServer);
        Response response = rs.getResponse(inFromServer);
        if (!response.getExitStatus()) {
            logInMessage.setText(response.getMessage());
            logInPassword.setText("");
        } else {
            logInMessage.setText(response.getMessage());
            startMainApp(event);
        }
    }

    private void startMainApp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/main.fxml"));
        root = loader.load();
        SceneController controller = loader.getController();
        controller.setRequestSender(rs);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void completeRegister(ActionEvent event) throws IOException, ClassNotFoundException {
        String username = usernameRegister.getText();
        String password_1 = registerPassword_1.getText();
        String password_2 = registerPassword_2.getText();
        if (!registerPassword_1.equals(registerPassword_2)) {
            registerMessage.setText("Пароли не совпадают!"); //todo fix
            registerPassword_1.setText("");
            registerPassword_2.setText("");
        } else {
            rs.sendRequest(new Request("register", new User(username, password_1)), outToServer);
            Response response = rs.getResponse(inFromServer);
            if (!response.getExitStatus()) {
                registerMessage.setText(response.getMessage());
            } else {
                registerMessage.setText(response.getMessage());
            }
            System.out.printf("%s %s %s", username, password_1, password_2);
        }
    }


}
