package fx.controllers;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.database.User;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SceneController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Label username;
    @FXML
    private Label current_user;
    /// table
    @FXML
    private TableView<MusicBand> table;
    @FXML
    private TableColumn<MusicBand, Double> album_sales;
    @FXML
    private TableColumn<MusicBand, Long> album_length;
    @FXML
    private TableColumn<MusicBand, Long> album_tracks;
    @FXML
    private TableColumn<MusicBand, String> album_name;
    @FXML
    private TableColumn<MusicBand, MusicGenre> genre;
    @FXML
    private TableColumn<MusicBand, LocalDate> establishmentdate;
    @FXML
    private TableColumn<MusicBand, Long> singlescount;
    @FXML
    private TableColumn<MusicBand, Long> numberofparticipants;
    @FXML
    private TableColumn<MusicBand, LocalDate> creationdate;
    @FXML
    private TableColumn<MusicBand, Float> coordinates_y;
    @FXML
    private TableColumn<MusicBand, Integer> coordinates_x;
    @FXML
    private TableColumn<MusicBand, String> name;
    @FXML
    private TableColumn<MusicBand, String> owner;
    @FXML
    private TableColumn<MusicBand, Long> id;
    ///
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
    private Stage stage;
    private Scene scene;
    private Parent root;
    private RequestSender rs;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    private SceneController current_controller;
    private static Collection<MusicBand> collection;
    private String current_username;

    private void startMainApp(ActionEvent event) throws IOException {
        changeScene(event, "/fx/main.fxml");

    }
    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(getResource().getString("exit"));
        alert.setHeaderText(getResource().getString("logout?"));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            /*rs.sendRequest(new Request("logout"), outToServer);*/
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    private ResourceBundle getResource() {
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
        if (backFromRegisterButton != null)
            backFromRegisterButton.setText(getResource().getString("backFromRegisterButton"));
        if (completeRegisterButton != null)
            completeRegisterButton.setText(getResource().getString("completeRegisterButton"));
        if (current_user != null) current_user.setText(getResource().getString("current_user"));
        if (logoutButton != null) logoutButton.setText(getResource().getString("logoutButton"));

        //table
        if (id != null) id.setText(getResource().getString("id"));
        if (owner != null) owner.setText(getResource().getString("owner"));
        if (name != null) name.setText(getResource().getString("name"));
        if (coordinates_x != null) coordinates_x.setText(getResource().getString("coordinates_x"));
        if (coordinates_y != null) coordinates_y.setText(getResource().getString("coordinates_y"));
        if (creationdate != null) creationdate.setText(/*LocalDate TODO*/getResource().getString("creationdate"));
        if (numberofparticipants != null) numberofparticipants.setText(getResource().getString("numberofparticipants"));
        if (singlescount != null) singlescount.setText(getResource().getString("singlescount"));
        if (establishmentdate != null) establishmentdate.setText(getResource().getString("establishmentdate"));
        if (genre != null) genre.setText(getResource().getString("genre"));
        if (album_name != null) album_name.setText(getResource().getString("album_name"));
        if (album_tracks != null) album_tracks.setText(getResource().getString("album_tracks"));
        if (album_length != null) album_length.setText(getResource().getString("album_length"));
        if (album_sales != null) album_sales.setText(getResource().getString("album_sales"));
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
        root = loader.load();
        current_controller = loader.getController();
        current_controller.setRequestSender(rs);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
//        stage.setResizable(false);
        if (url.equals("/fx/main.fxml")) {
            stage.setMinHeight(500);
            stage.setMinWidth(800);
//            stage.setFullScreen(true);
            stage.setResizable(true);
            current_controller.username.setText(current_username);
        }
        current_controller.changeLanguage(LanguageManager.getCurrentLanguage());
        current_controller.setLanguageBox();
    }

    @FXML
    private void completeLogin(ActionEvent event) throws IOException, ClassNotFoundException {
        current_username = usernameLogIn.getText();
        String password = logInPassword.getText();
        rs.sendRequest(new Request("login", new User(current_username, password, LanguageManager.getBundle().getLocale())), outToServer);
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

        if (table != null) {
            table.setPrefSize(960, 360);
            /*Collection<MusicBand> collection;
            try {
                collection = rs.getResponse(inFromServer).getMusicBandsCollection();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }*/

            id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
            name.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
            owner.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner()));
            coordinates_x.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
            coordinates_y.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
            creationdate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
            numberofparticipants.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumberOfParticipants()));
            singlescount.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSinglesCount()));
            establishmentdate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEstablishmentDate()));
            genre.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getGenre()));
            album_name.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBestAlbum().getName()));
            album_tracks.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBestAlbum().getTracks()));
            album_length.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBestAlbum().getLength()));
            album_sales.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBestAlbum().getSales()));
            ObservableList<MusicBand> observableList = FXCollections.observableArrayList(collection);
//            observableList.addListener(); todo
            table.setItems(observableList);
            System.out.println(table.getItems());
        }

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
    @FXML
    private void add(ActionEvent actionEvent) {
    }
    @FXML
    private void addIfMax(ActionEvent actionEvent) {
    }
    @FXML
    private void addIfMin(ActionEvent actionEvent) {
    }
    @FXML
    private void clear(ActionEvent actionEvent) {
    }
    @FXML
    private void executeScript(ActionEvent actionEvent) {
    }
    @FXML
    private void showScripts(ActionEvent actionEvent) {
    }
    @FXML
    private void help(ActionEvent actionEvent) {
    }
    @FXML
    private void info(ActionEvent actionEvent) {
    }
    @FXML
    private void remove(ActionEvent actionEvent) {
    }
    @FXML
    private void removeGreater(ActionEvent actionEvent) {
    }
}
