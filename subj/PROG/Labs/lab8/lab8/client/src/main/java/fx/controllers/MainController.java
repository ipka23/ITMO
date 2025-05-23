package fx.controllers;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.database.User;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import fx.ResponseHandler;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Setter;
import network.RequestSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;

public class MainController extends SceneController implements Initializable {
    @FXML
    public ComboBox<String> languageBox;
    @FXML
    private Button logoutButton;
    @FXML
    public Label username;
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
    @Setter
    public static Collection<MusicBand> collection;
    @Setter
    private RequestSender rs;
    @Setter
    private ObjectOutputStream outToServer;
    @Setter
    private ObjectInputStream inFromServer;
    @Setter
    private User currentUser;
    private ObservableList<MusicBand> observableList;
    private ResponseHandler handler;

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        addIcon(alert, new Image("images/exit.png"));
        alert.setTitle(getResource().getString("exit"));
        alert.setHeaderText(getResource().getString("logout?"));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            rs.sendRequest(new Request("exit"), outToServer);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageBox.setValue(LanguageManager.getCurrentLanguage());
        table.setPrefSize(960, 360);

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
//        observableList = FXCollections.observableArrayList(collection);
//        table.setItems(observableList);
        observableList = FXCollections.observableArrayList();
        table.setItems(observableList);

//        System.out.println(table.getItems());

    }

    public void init(Collection<MusicBand> collection) {
        inFromServer = rs.getInFromServer();
        outToServer = rs.getOutToServer();

        observableList.setAll(collection);
        System.out.println("observableList: " + System.identityHashCode(observableList));
//        table.setItems(observableList);
       /* if (handler == null) {
            handler = new ResponseHandler(inFromServer, observableList);
            handler.start();
        }*/
    }

    public void changeLanguage(String lang) {
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


    private void addIcon(Dialog<MusicBand> dialog, Image icon) {
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
    }

    private void addIcon(Alert alert, Image icon) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
    }

    private void errorAlert(Response response) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        addIcon(alert, new Image("images/angry.png"));
        alert.setTitle(getResource().getString("error"));
        alert.setHeaderText(response.getMessage());
        alert.show();
    }

    private void infoAlert(Response response) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        addIcon(alert, new Image("images/pikachu.png"));
        alert.setTitle(getResource().getString("success"));
        alert.setHeaderText(response.getMessage());
        alert.show();
    }

    @FXML
    private void add(ActionEvent event) {
        Dialog<MusicBand> dialog = new Dialog<>();
        DialogPane pane = dialog.getDialogPane();
        Image icon = new Image("images/pikachu2.png");
        addIcon(dialog, icon);

        Label Lname = new Label(getResource().getString("name"));
        Label Lcoordinates_x = new Label(getResource().getString("coordinates_x"));
        Label Lcoordinates_y = new Label(getResource().getString("coordinates_y"));
        Label Lnumberofparticipants = new Label(getResource().getString("numberofparticipants"));
        Label Lsinglescount = new Label(getResource().getString("singlescount"));
        Label Lestablishmentdate = new Label(getResource().getString("establishmentdate"));
        Label Lgenre = new Label(getResource().getString("genre"));
        Label Lalbum_name = new Label(getResource().getString("album_name"));
        Label Lalbum_tracks = new Label(getResource().getString("album_tracks"));
        Label Lalbum_length = new Label(getResource().getString("album_length"));
        Label Lalbum_sales = new Label(getResource().getString("album_sales"));

        TextField nameTF = new TextField();
        TextField coordinates_xTF = new TextField();
        TextField coordinates_yTF = new TextField();
        TextField numberofparticipantsTF = new TextField();
        TextField singlescountTF = new TextField();
        TextField establishmentdateTF = new TextField();

        ComboBox<MusicGenre> genreCB = new ComboBox<>();
        genreCB.setPromptText("POP");
        genreCB.getItems().addAll(MusicGenre.values());

        TextField album_nameTF = new TextField();
        TextField album_tracksTF = new TextField();
        TextField album_lengthTF = new TextField();
        TextField album_salesTF = new TextField();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


       /* genreCB.setOnAction(actionEvent -> {
            switch (genreCB.getPromptText()){
                case "PROGRESSIVE_ROCK":
            }
        });*/

        int row = 0;
        grid.add(Lname, 0, row);
        grid.add(nameTF, 1, row++);

        grid.add(Lcoordinates_x, 0, row);
        grid.add(coordinates_xTF, 1, row++);

        grid.add(Lcoordinates_y, 0, row);
        grid.add(coordinates_yTF, 1, row++);

        grid.add(Lnumberofparticipants, 0, row);
        grid.add(numberofparticipantsTF, 1, row++);

        grid.add(Lsinglescount, 0, row);
        grid.add(singlescountTF, 1, row++);

        grid.add(Lestablishmentdate, 0, row);
        grid.add(establishmentdateTF, 1, row++);

        grid.add(Lgenre, 0, row);
        grid.add(genreCB, 1, row++);

        grid.add(Lalbum_name, 0, row);
        grid.add(album_nameTF, 1, row++);

        grid.add(Lalbum_tracks, 0, row);
        grid.add(album_tracksTF, 1, row++);

        grid.add(Lalbum_length, 0, row);
        grid.add(album_lengthTF, 1, row++);

        grid.add(Lalbum_sales, 0, row);
        grid.add(album_salesTF, 1, row++);

        ButtonType confirm = new ButtonType(getResource().getString("confirm"), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType(getResource().getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(confirm, cancel);
        pane.setContent(grid);
        dialog.setTitle(getResource().getString("add"));
        dialog.setResultConverter(button -> {
            if (button == confirm) {
                MusicBand band = null;
                try {
                    String name = nameTF.getText();
                    Integer coordinates_x = Integer.parseInt(coordinates_xTF.getText());
                    Float coordinates_y = Float.parseFloat(coordinates_yTF.getText());
                    Long numberofparticipants = Long.parseLong(numberofparticipantsTF.getText());
                    Long singlescount = Long.parseLong(singlescountTF.getText());
                    LocalDate establishmentdate = LocalDate.parse(establishmentdateTF.getText());
                    MusicGenre genre = MusicGenre.valueOf(genreCB.getPromptText());
                    String album_name = album_nameTF.getText();
                    Long album_tracks = Long.parseLong(album_tracksTF.getText());
                    Long album_length = Long.parseLong(album_lengthTF.getText());
                    Double album_sales = Double.parseDouble(album_salesTF.getText());
                    // TODO (отправить на сервер, добавить в бд, если ошибка то вывод в Alert, иначе добавить в коллекцию и gui таблицу)
                    band = new MusicBand(username.getText(), name, new Coordinates(coordinates_x, coordinates_y), numberofparticipants, singlescount, establishmentdate, genre, new Album(album_name, album_tracks, album_length, album_sales));
                    rs.sendRequest(new Request("add", currentUser, band), outToServer);
//                    Response response = rs.getResponse(inFromServer);
//                    Response response = handler.getResponse(); todo
                    Response response = (Response) inFromServer.readObject();

                    /*while (response.getMessage().equals("refresh")) {
                        Collection<MusicBand> collection = response.getMusicBandsCollection();
                        Platform.runLater(() -> observableList.setAll(collection));
                        response = rs.getResponse(inFromServer);
                    }*/

                    if (!response.getExitStatus()) {
                        errorAlert(response);
                    } else {
                        infoAlert(response);
                        observableList.add(response.getMusicBand());
                        System.out.println(response.getMusicBand());
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(getResource().getString("error"));
                    Image icon2 = new Image("images/angry.png");
                    addIcon(alert, icon2);
                    alert.setHeaderText(getResource().getString("errorMessage"));
                    alert.show();
                    e.printStackTrace();
                }
            }
            return null;
        });

        dialog.showAndWait();

    }


    @FXML
    private void addIfMax(ActionEvent e) {
    }

    @FXML
    private void addIfMin(ActionEvent e) {
    }

    @FXML
    private void clear(ActionEvent e) {
    }

    @FXML
    private void executeScript(ActionEvent e) {
    }

    @FXML
    private void showScripts(ActionEvent e) {
    }

    @FXML
    private void help(ActionEvent e) {
    }

    @FXML
    private void info(ActionEvent e) {
    }

    @FXML
    private void remove(ActionEvent e) {
    }

    @FXML
    private void removeGreater(ActionEvent e) {
    }
}
