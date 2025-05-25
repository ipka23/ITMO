package fx.controllers;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.database.User;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import network.ResponseHandler;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

    public Button add_if_max;
    public Button add_if_min;
    public Button help;
    public Button clear;
    public Button remove;
    public Button updateButton;
    public Button remove_greater;
    public Button info;
    @FXML
    public StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button add;
    @FXML
    public ComboBox<String> languageBox;
    @FXML
    private ComboBox<String> filterByBox;
    @FXML
    private ComboBox<String> sortByBox;
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
    private RequestSender sender;
    @Setter
    private ObjectOutputStream outToServer;
    @Setter
    private ObjectInputStream inFromServer;
    @Setter
    private User currentUser;
    private ObservableList<MusicBand> observableList;
    private ResponseHandler handler;
    private VisualizationController visualController;

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        addIcon(alert, new Image("images/exit.png"));
        alert.setTitle(getResource().getString("exit"));
        alert.setHeaderText(getResource().getString("logout?"));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            sender.sendRequest(new Request("exit"), outToServer);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
        // привязка значений из musicBand к столбцам таблицы
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

        // привязываем нашу коллекцию в интерфейс
        observableList = FXCollections.observableArrayList();
        table.setItems(observableList);
        add.setOnAction(event -> {
            add(event, "add");
        });
        setFilterByBox();
        setSortByBox();
//        System2DController.colorsMap = new HashMap<>();
//        Color color = Color;

        remove.setOnAction(event -> {
            MusicBand selected = table.getSelectionModel().getSelectedItem();
            try {
                if (selected == null) {
                    errorAlert(getResource().getString("chooseBandToDelete"));
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    addIcon(alert, new Image("images/exit.png"));
                    alert.setTitle(getResource().getString("remove"));
                    alert.setHeaderText(getResource().getString("remove?"));
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        remove(event, selected);
                    }
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        updateButton.setOnAction(event -> {
            try {
                errorAlert(getResource().getString("chooseBandToUpdate"));
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

//        Pane dpsk = draw2DCoordinateSystem();
//        stackPane.getChildren().add(dpsk);
//        visualController = new VisualizationController();
        Pane coordinateSystem = VisualizationController.draw2DCoordinateSystem();
        stackPane.getChildren().add(coordinateSystem);
    }

    private void setSortByBox() {
        sortByBox.setValue(getResource().getString("id"));
    }

    private void setFilterByBox() {
    }

    public void init(Collection<MusicBand> collection) {
        inFromServer = sender.getInFromServer();
        outToServer = sender.getOutToServer();

        observableList.setAll(collection);
        handler = new ResponseHandler(inFromServer, observableList);
        handler.start();

        for (MusicBand band : collection) {
            VisualizationController.drawMusicBand(band.getCoordinates().getX(), band.getCoordinates().getY(), VisualizationController.getColor(band));
        }

    }



    public void changeLanguage() {
        // top_left anchor
        languageBox.setValue(LanguageManager.getCurrentLanguage());
        current_user.setText(getResource().getString("current_user"));
        logoutButton.setText(getResource().getString("logoutButton"));

        //table
        id.setText(getResource().getString("id"));
        owner.setText(getResource().getString("owner"));
        name.setText(getResource().getString("name"));
        coordinates_x.setText(getResource().getString("coordinates_x"));
        coordinates_y.setText(getResource().getString("coordinates_y"));
        creationdate.setText(/*LocalDate TODO*/getResource().getString("creationdate"));
        numberofparticipants.setText(getResource().getString("numberofparticipants"));
        singlescount.setText(getResource().getString("singlescount"));
        establishmentdate.setText(getResource().getString("establishmentdate"));
        genre.setText(getResource().getString("genre"));
        album_name.setText(getResource().getString("album_name"));
        album_tracks.setText(getResource().getString("album_tracks"));
        album_length.setText(getResource().getString("album_length"));
        album_sales.setText(getResource().getString("album_sales"));

        // commands
        add.setText(getResource().getString("add"));
        add_if_max.setText(getResource().getString("add_if_max"));
        add_if_min.setText(getResource().getString("add_if_min"));
        help.setText(getResource().getString("help"));
        clear.setText(getResource().getString("clear"));
        remove.setText(getResource().getString("remove"));
        remove_greater.setText(getResource().getString("remove_greater"));
        updateButton.setText(getResource().getString("update"));
        info.setText(getResource().getString("info"));

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

    private void errorAlert(String message) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        addIcon(alert, new Image("images/angry.png"));
        alert.setTitle(getResource().getString("error"));
        alert.setHeaderText(message);
        alert.show();
    }

    private void infoAlert(Response response) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        addIcon(alert, new Image("images/pikachu.png"));
        alert.setTitle(getResource().getString("success"));
        alert.setHeaderText(response.getMessage());
        alert.show();
    }


    private void add(ActionEvent event, String command) {
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
        dialog.setTitle(getResource().getString(command));
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
                    // отправить на сервер, добавить в бд, если ошибка то вывод в Alert, иначе добавить в коллекцию и gui таблицу
                    band = new MusicBand(username.getText(), name, new Coordinates(coordinates_x, coordinates_y), numberofparticipants, singlescount, establishmentdate, genre, new Album(album_name, album_tracks, album_length, album_sales));
                    sender.sendRequest(new Request(command, currentUser, band), outToServer);

                    Response response = handler.getResponse();
                    while (response.getMessage().equals("refresh") || response.getMessage().equals("delete_refresh")) {
                        response = handler.getResponse();
                    }
                    if (!response.getExitStatus()) {
                        errorAlert(response);
                    } else {
                        double x = Double.parseDouble(coordinates_xTF.getText());
                        double y = Double.parseDouble(coordinates_yTF.getText());
                        VisualizationController.drawMusicBand(x, y, VisualizationController.getColor(band));
                        infoAlert(response);
//                        observableList.add(response.getMusicBand());
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
        add(e, "add_if_max");
    }

    @FXML
    private void addIfMin(ActionEvent e) {
        add(e, "add_if_min");
    }

    @FXML
    private void clear(ActionEvent e) throws IOException, ClassNotFoundException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(getResource().getString("clear"));
        Image icon = new Image("images/angry.png");
        addIcon(alert, icon);
        alert.setHeaderText(getResource().getString("clear?"));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            sender.sendRequest(new Request("clear", currentUser), outToServer);
            Response response = handler.getResponse();
            if (!response.getExitStatus()) {
                errorAlert(response);
            } else {
                infoAlert(response);
            }
        }

    }

    @FXML
    private void executeScript(ActionEvent e) {
    }

    @FXML
    private void showScripts(ActionEvent e) {
    }

    @FXML
    private void help(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendRequest(new Request("help", currentUser), outToServer);
        Response response = handler.getResponse();
        infoAlert(response);
    }

    @FXML
    private void info(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendRequest(new Request("info", currentUser), outToServer);
        Response response = handler.getResponse();
        infoAlert(response);
    }

    private void remove(ActionEvent e, MusicBand band) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendRequest(new Request("remove " + band.getId()), outToServer);
        Response r = handler.getResponse();
        if (!r.getExitStatus()) {
            errorAlert(r);
        } else {
            double x = band.getCoordinates().getX();
            double y = band.getCoordinates().getY();
            VisualizationController.eraseMusicBand(x, y, VisualizationController.getColor(band));
            infoAlert(r);
        }
    }

    @FXML
    private void removeGreater(ActionEvent e) {
    }

    @FXML
    private void update(ActionEvent e) {

    }

}