package fx.controllers;

import common_utility.database.User;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import network.RequestSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
    @FXML
    private TreeTableView treeTable;
    @FXML
    public Button logInButton;
    @FXML
    public Button registerButton;
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
    @FXML
    public Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button backFromLogInButton;
    @FXML
    private Button completeLoginButton;
    @FXML
    private Label enterUsernameLabel;
    @FXML
    private Label enterPasswordLabel_1;
    @FXML
    private Label enterPasswordLabel_2;
    @FXML
    private Button backFromRegisterButton;
    @FXML
    private Button completeRegisterButton;
    @FXML
    public ComboBox languageBox;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private RequestSender rs;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    private SceneController current_controller;

    public SceneController() {

    }
    public ResourceBundle getResource() {
        return LanguageManager.getBundle();
    }
    public void changeLanguage(String lang) {
        languageBox.setValue(lang);
        if (logInButton != null) logInButton.setText(getResource().getString("logInButton"));
        if (registerButton != null) registerButton.setText(getResource().getString("registerButton"));
        if (passwordLabel != null) passwordLabel.setText(getResource().getString("passwordLabel"));
        if (usernameLabel != null) usernameLabel.setText(getResource().getString("usernameLabel"));
        if (backFromLogInButton != null) backFromLogInButton.setText(getResource().getString("backFromLogInButton"));
        if (completeLoginButton != null) completeLoginButton.setText(getResource().getString("completeLoginButton"));
        if (enterUsernameLabel != null) enterUsernameLabel.setText(getResource().getString("enterUsernameLabel"));
        if (enterPasswordLabel_1 != null) enterPasswordLabel_1.setText(getResource().getString("enterPasswordLabel_1"));
        if (enterPasswordLabel_2 != null) enterPasswordLabel_2.setText(getResource().getString("enterPasswordLabel_2"));
        if (backFromRegisterButton != null) backFromRegisterButton.setText(getResource().getString("backFromRegisterButton"));
        if (completeRegisterButton != null) completeRegisterButton.setText(getResource().getString("completeRegisterButton"));
    }

    public void setRequestSender(RequestSender rs) {
        this.rs = rs;
        this.outToServer = rs.getOutToServer();
        this.inFromServer = rs.getInFromServer();
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/fx/login.fxml");

    }

    @FXML
    public void onRegisterButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/fx/register.fxml");
    }

    @FXML
    public void backFromRegister(ActionEvent event) throws IOException {
        changeScene(event, "/fx/auth.fxml");
    }

    @FXML
    public void backFromLogin(ActionEvent event) throws IOException {
        changeScene(event, "/fx/auth.fxml");
    }

    public void changeScene(ActionEvent event, String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url), LanguageManager.getBundle());
        root = loader.load();
        current_controller = loader.getController();
        current_controller.setRequestSender(rs);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();



        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if (url.equals("/fx/main.fxml")) {
            stage.setMinHeight(500);
            stage.setMinWidth(800);
            stage.setFullScreen(true);
            stage.setResizable(true);
        }
        current_controller.changeLanguage(LanguageManager.getCurrentLanguage());
        current_controller.setLanguageBox();
    }

    @FXML
    public void completeLogin(ActionEvent event) throws IOException, ClassNotFoundException {
        String username = usernameLogIn.getText();
        String password = logInPassword.getText();
        rs.sendRequest(new Request("login", new User(username, password, LanguageManager.getBundle().getLocale())), outToServer);
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
        changeScene(event, "/fx/main.fxml");
        treeTable.setPrefSize(800, 400);
        treeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void completeRegister(ActionEvent event) throws IOException, ClassNotFoundException {
        String username = usernameRegister.getText();
        String password_1 = registerPassword_1.getText();
        String password_2 = registerPassword_2.getText();
        if (!password_1.equals(password_2)) {
            registerMessage.setText(getResource().getString("passwords_dont_match"));
            registerPassword_1.setText("");
            registerPassword_2.setText("");
        } else {
            rs.sendRequest(new Request("register", new User(username, password_1, LanguageManager.getBundle().getLocale())), outToServer);
            Response response = rs.getResponse(inFromServer);
            if (!response.getExitStatus()) {
                registerMessage.setText(response.getMessage());
                usernameRegister.setText("");
                registerPassword_1.setText("");
                registerPassword_2.setText("");
            } else {
                registerMessage.setText(response.getMessage());
                startMainApp(event);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageBox.setValue(LanguageManager.getCurrentLanguage());
    }


    public void setLanguageBox() {
        ObservableList<String> languages = FXCollections.observableList(List.of("Русский", "English", "Deutsch", "Български"));
        languageBox.setItems(languages);
        languageBox.setOnAction(event -> {
            String current_language = (String) languageBox.getValue();
            switch (current_language) {
                case "English":
                    LanguageManager.setBundle(LanguageManager.getEn_bundle());
                    LanguageManager.setCurrentLanguage(current_language);
                    changeLanguage(current_language);
                    break;
                case "Русский":
                    LanguageManager.setBundle(LanguageManager.getRu_bundle());
                    LanguageManager.setCurrentLanguage(current_language);
                    changeLanguage(current_language);
                    break;
                case "Deutsch":
                    LanguageManager.setBundle(LanguageManager.getDe_bundle());
                    LanguageManager.setCurrentLanguage(current_language);
                    changeLanguage(current_language);
                    break;
                case "Български":
                    LanguageManager.setBundle(LanguageManager.getBg_bundle());
                    LanguageManager.setCurrentLanguage(current_language);
                    changeLanguage(current_language);
                    break;
            }
        });
    }
}
