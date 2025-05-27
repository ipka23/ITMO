package fx.controllers;

import common_entities.MusicBand;
import common_utility.database.User;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import network.RequestSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
    @FXML
    private Button logInButton;
    @FXML
    private Button registerButton;
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
    public ComboBox<String> languageBox;
    private RequestSender rs;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    private User currentUser;
    private String current_username;
    private static Collection<MusicBand> collection;
    private void startMainApp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setSender(rs);
        controller.setInFromServer(inFromServer);
        controller.setOutToServer(outToServer);
//        controller.setCurrent_username(current_username);
        controller.setCurrentUser(currentUser);
        controller.username.setText(current_username);
        controller.init(collection);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
        stage.setMaximized(true);
//        stage.setFullScreen(true);
//        controller.setLanguageBox();

//        controller.changeLanguage(LanguageManager.getCurrentLanguage());
//        MainController.setCollection(collection);


    }

    protected ResourceBundle getResource() {
        return LanguageManager.getBundle();
    }

    private void changeLanguage(String lang) {
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
    private void onLoginButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/fx/login.fxml");

    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/fx/register.fxml");
    }

    @FXML
    private void backFromRegister(ActionEvent event) throws IOException {
        changeScene(event, "/fx/auth.fxml");
    }

    @FXML
    private void backFromLogin(ActionEvent event) throws IOException {
        changeScene(event, "/fx/auth.fxml");
    }

    private void changeScene(ActionEvent event, String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url), LanguageManager.getBundle());
        Parent root = loader.load();
        SceneController current_controller = loader.getController();
        current_controller.setRequestSender(rs);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
//        stage.setResizable(false);
        current_controller.changeLanguage(LanguageManager.getCurrentLanguage());
        current_controller.setLanguageBox();
    }

    @FXML
    private void completeLogin(ActionEvent event) throws IOException, ClassNotFoundException {
        current_username = usernameLogIn.getText();
        String password = logInPassword.getText();
        currentUser = new User(current_username, password, LanguageManager.getBundle().getLocale());
        rs.sendRequest(new Request("login", currentUser), outToServer);
        Response response = rs.getResponse(inFromServer);
        collection = response.getMusicBandsCollection();
        if (!response.getExitStatus()) {
            logInMessage.setText(response.getMessage());
            logInPassword.setText("");
        } else {
            logInMessage.setText(response.getMessage());
            startMainApp(event);
        }
    }


    @FXML
    private void completeRegister(ActionEvent event) throws IOException, ClassNotFoundException {
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
        /*Platform.runLater(() -> {
            Stage stage = (Stage) languageBox.getScene().getWindow();
            stage.setOnCloseRequest(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                s.getIcons().add(new Image("images/exit.png"));
                alert.setTitle(getResource().getString("exit"));
                alert.setHeaderText(getResource().getString("logout?"));
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    s.close();
                } else {
                    stage.showAndWait();
                }
            });
        });*/
    }


    public void setLanguageBox() {
        ObservableList<String> languages = FXCollections.observableList(List.of("Русский", "English", "Deutsch", "Български"));
        languageBox.setItems(languages);
        languageBox.setOnAction(event -> {
            String current_language = languageBox.getValue();
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
