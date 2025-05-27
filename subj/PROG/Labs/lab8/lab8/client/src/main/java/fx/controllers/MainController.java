package fx.controllers;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.database.User;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import javafx.geometry.HPos;
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
import javafx.stage.Stage;
import lombok.Setter;
import network.RequestSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MainController extends SceneController implements Initializable {
    @FXML
    private Label filterByLabel;
    @FXML
    private Button reset;
    @FXML
    private Button add_if_max;
    @FXML
    private Button add_if_min;
    @FXML
    private Button help;
    @FXML
    private Button clear;
    @FXML
    private Button remove;
    @FXML
    private Button updateButton;
    @FXML
    private Button remove_greater;
    @FXML
    private Button info;
    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button add;
    @FXML
    public ComboBox<String> languageBox;
    @FXML
    private ComboBox<String> filterByBox;
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
    public Collection<MusicBand> collection;
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
    private VisualizationController visualizationController;
    private ObservableList<MusicBand> notFilteredList;
    @FXML
    public void logout(ActionEvent event) {
        try {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguageBox();
        changeLanguage();
        initTable();
        setButtonActions();
    }

    private void initCoordinateSystem(VisualizationController visualizationController) {
        Pane coordinateSystem = visualizationController.draw2DCoordinateSystem();
        stackPane.getChildren().add(coordinateSystem);
    }

    private void setButtonActions() {
        add.setOnAction(event -> {
            add(event, "add");
        });
        remove.setOnAction(event -> {
            MusicBand selected = table.getSelectionModel().getSelectedItem();
            try {
                if (selected == null) {
                    errorAlert(getResource().getString("chooseBandToDelete"));
                } else if (!selected.getOwner().equals(currentUser.getUsername())) {
                    errorAlert(getResource().getString("deletePermissionDenied"));
                } else {
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
                MusicBand selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    errorAlert(getResource().getString("chooseBandToUpdate"));
                } else if (!selected.getOwner().equals(currentUser.getUsername())) {
                    errorAlert(getResource().getString("updatePermissionDenied"));
                } else {
                    update(event, selected);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
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
                    changeLanguage();
                    break;
                case "Русский":
                    LanguageManager.setBundle(LanguageManager.getRu_bundle());
                    LanguageManager.setCurrentLanguage(current_language);
                    changeLanguage();
                    break;
                case "Deutsch":
                    LanguageManager.setBundle(LanguageManager.getDe_bundle());
                    LanguageManager.setCurrentLanguage(current_language);
                    changeLanguage();
                    break;
                case "Български":
                    LanguageManager.setBundle(LanguageManager.getBg_bundle());
                    LanguageManager.setCurrentLanguage(current_language);
                    changeLanguage();
                    break;
            }
        });
    }
    private void initTable() {
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
    }


    public void init(Collection<MusicBand> collection) {
        inFromServer = sender.getInFromServer();
        outToServer = sender.getOutToServer();

        observableList.setAll(collection);
        notFilteredList = FXCollections.observableArrayList(collection);
        VisualizationController visualizationController = new VisualizationController();
        this.visualizationController = visualizationController;
        initCoordinateSystem(visualizationController);
        handler = new ResponseHandler(inFromServer, observableList, visualizationController);
        handler.start();

        for (MusicBand band : collection) {
            visualizationController.drawMusicBand(band);
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
        numberofparticipants.setText(getResource().getString("numberOfParticipants"));
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
        reset.setText(getResource().getString("reset"));
        filterByLabel.setText(getResource().getString("filterByLabel"));

        setFilterByBox();
    }


    private void addIcon(Dialog<MusicBand> dialog, Image icon) {
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
    }

    private void addIcon(Alert alert, Image icon) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
    }

    private void errorAlert(Response response) {
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
        Label Lnumberofparticipants = new Label(getResource().getString("numberOfParticipants"));
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
                    while (response.getMessage().equals("add_refresh") || response.getMessage().equals("delete_refresh")) {
                        response = handler.getResponse();
                    }
                    if (!response.getExitStatus()) {
                        errorAlert(response);
                    } else {
                        visualizationController.drawMusicBand(band);
                        infoAlert(response);
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
    private void help(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendRequest(new Request("help", currentUser), outToServer);
        Response response = handler.getResponse();
        infoAlert(response);
    }

    @FXML
    private void info(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendRequest(new Request("info", currentUser), outToServer);
        Response response = handler.getResponse();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(response.getMessage());
        alert.setTitle(getResource().getString("info"));
        alert.setHeaderText(getResource().getString("collectionInfo"));
        addIcon(alert, new Image("images/info.png"));
        alert.showAndWait();

    }

    private void remove(ActionEvent e, MusicBand band) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendRequest(new Request("remove", currentUser, band), outToServer);
        Response r = handler.getResponse();
        if (!r.getExitStatus()) {
            errorAlert(r);
        } else {
            visualizationController.eraseMusicBand(band, visualizationController.getColor(band));
            infoAlert(r);
        }
    }

    @FXML
    private void removeGreater(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException {
        add(e, "remove_greater");
        /*sender.sendRequest(new Request("remove_greater"), outToServer);
        Response r = handler.getResponse();
        if (!r.getExitStatus()) {
            errorAlert(r);
        } else {
            infoAlert(r);
        }*/
    }

    private void update(ActionEvent e, MusicBand selected) {
        String command = "update";
        Dialog<MusicBand> dialog = new Dialog<>();
        DialogPane pane = dialog.getDialogPane();
        Image icon = new Image("images/pikachu2.png");
        addIcon(dialog, icon);

        Label Lname = new Label(getResource().getString("name"));
        Label Lcoordinates_x = new Label(getResource().getString("coordinates_x"));
        Label Lcoordinates_y = new Label(getResource().getString("coordinates_y"));
        Label Lnumberofparticipants = new Label(getResource().getString("numberOfParticipants"));
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
        dialog.setTitle(getResource().getString("update"));

        nameTF.setText(selected.getName());
        coordinates_xTF.setText(String.valueOf(selected.getCoordinates().getX()));
        coordinates_yTF.setText(String.valueOf(selected.getCoordinates().getY()));
        numberofparticipantsTF.setText(String.valueOf(selected.getNumberOfParticipants()));
        singlescountTF.setText(String.valueOf(selected.getSinglesCount()));
        establishmentdateTF.setText(String.valueOf(selected.getEstablishmentDate()));
        album_nameTF.setText(selected.getBestAlbum().getName());
        album_tracksTF.setText(String.valueOf(selected.getBestAlbum().getTracks()));
        album_lengthTF.setText(String.valueOf(selected.getBestAlbum().getLength()));
        album_salesTF.setText(String.valueOf(selected.getBestAlbum().getSales()));
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
                    band = new MusicBand(username.getText(), name, new Coordinates(coordinates_x, coordinates_y), numberofparticipants, singlescount, establishmentdate, genre, new Album(album_name, album_tracks, album_length, album_sales));
                    band.setId(selected.getId());
                    sender.sendRequest(new Request(command, currentUser, selected, band), outToServer);
                    Response response = handler.getResponse();
                    while (response.getMessage().equals("update_refresh") || response.getMessage().equals("add_refresh") ) {
                        response = handler.getResponse();
                    }
                    if (!response.getExitStatus()) {
                        errorAlert(response);
                    } else {
                        visualizationController.eraseMusicBand(selected, visualizationController.getColor(selected));
                        visualizationController.drawMusicBand(band);
                        infoAlert(response);
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(getResource().getString("error"));
                    Image icon2 = new Image("images/angry.png");
                    addIcon(alert, icon2);
                    alert.setHeaderText(getResource().getString("errorMessage"));
                    alert.show();
                    ex.printStackTrace();
                }
            }
            return null;
        });
        dialog.showAndWait();

    }

    public void setFilterByBox(){
        ObservableList<String> sortBy = FXCollections.observableList(List.of(
                getResource().getString("id"),
                getResource().getString("owner"),
                getResource().getString("name"),
                getResource().getString("coordinates_x"),
                getResource().getString("coordinates_y"),
                getResource().getString("creationdate"),
                getResource().getString("numberOfParticipants"),
                getResource().getString("singlescount"),
                getResource().getString("establishmentdate"),
                getResource().getString("genre"),
                getResource().getString("album_name"),
                getResource().getString("album_tracks"),
                getResource().getString("album_length"),
                getResource().getString("album_sales")
        ));
        filterByBox.setOnAction(null);
        filterByBox.setItems(sortBy);
        filterByBox.setValue(getResource().getString("album_sales"));
        filterByBox.setOnAction(event -> {
            String filterBy = filterByBox.getValue();
            Set<MusicBand> filtered = Set.of();
            String value;
//            String operator;
            if (filterBy.equals(getStr("id").trim())) {
                value = textDialog("id");
//                System.out.println(Arrays.toString(value.split(" ", 3)));
                String operator = value.split(" ", 3)[1];
                Long id = Long.parseLong(value.split(" ", 3)[2]);
                filtered = observableList
                        .stream()
                        .filter(band -> {
                            Long bandId = band.getId();
                            switch (operator) {
                                case ">": return bandId > id;
                                case "<": return bandId < id;
                                case ">=": return bandId >= id;
                                case "<=": return bandId <= id;
                                case "=": return bandId.equals(id);
                                default: return false;

                            }
                        }).collect(Collectors.toSet());
                System.out.println(" " + operator + " " + id);
            }
            if (filterBy.equals(getStr("owner"))) {
                value = textDialog("owner");
                String operator = value.split(" ", 3)[1];
                String owner = value.split(" ", 3)[2];
                filtered = observableList.stream().filter(band -> {
                    String bandOwner = band.getOwner();
                    switch (operator) {
                        case "=": return bandOwner.equals(owner);
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("name"))) {
                value = textDialog("name");
                String operator = value.split(" ", 3)[1];
                String name = value.split(" ", 3)[2];
                filtered = observableList.stream().filter(band -> {
                    String bandName = band.getName();
                    switch (operator) {
                        case "=": return bandName.startsWith(name);
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("coordinates_x"))) {
                value = textDialog("coordinates_x");
                String operator = value.split(" ", 3)[1];
                Integer x = Integer.parseInt(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    Integer bandX = band.getCoordinates().getX();
                    switch (operator) {
                        case "=": return bandX.equals(x);
                        case "<": return bandX < x;
                        case ">=": return bandX > x;
                        case "<=": return bandX <= x;
                        case ">": return bandX > x;
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("coordinates_y"))) {
                value = textDialog("coordinates_y");
                String operator = value.split(" ", 3)[1];
                Float y = Float.parseFloat(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    Float bandY = band.getCoordinates().getY();
                    switch (operator) {
                        case "=": return bandY.equals(y);
                        case "<": return bandY < y;
                        case ">=": return bandY > y;
                        case "<=": return bandY <= y;
                        case ">": return bandY > y;
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("creationdate"))) {
                value = textDialog("creationdate");
                String operator = value.split(" ", 3)[1];
                LocalDate creationDate = LocalDate.parse(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    LocalDate bandCreationDate = band.getCreationDate();
                    switch (operator) {
                        case "=": return bandCreationDate.equals(creationDate);
                        case ">": return bandCreationDate.isAfter(creationDate);
                        case "<": return bandCreationDate.isBefore(creationDate);
                        case "<=": return bandCreationDate.isBefore(creationDate) || bandCreationDate.equals(creationDate);
                        case ">=": return bandCreationDate.isAfter(creationDate) || bandCreationDate.equals(creationDate);
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("numberOfParticipants"))) {
                value = textDialog("numberOfParticipants");
                String operator = value.split(" ", 3)[1];
                Long numberOfParticipants = Long.parseLong(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    Long bandNumberOfParticipants = band.getNumberOfParticipants();
                    switch (operator) {
                        case "=": return bandNumberOfParticipants.equals(numberOfParticipants);
                        case ">": return bandNumberOfParticipants > numberOfParticipants;
                        case "<": return bandNumberOfParticipants < numberOfParticipants;
                        case "<=": return bandNumberOfParticipants <= numberOfParticipants;
                        case ">=": return bandNumberOfParticipants >= numberOfParticipants;
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("singlescount"))) {
                value = textDialog("singlescount");
                String operator = value.split(" ", 3)[1];
                Long singlesCount = Long.parseLong(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    Long bandSinglesCount = band.getNumberOfParticipants();
                    switch (operator) {
                        case "=": return bandSinglesCount.equals(singlesCount);
                        case ">": return bandSinglesCount > singlesCount;
                        case "<": return bandSinglesCount < singlesCount;
                        case "<=": return bandSinglesCount <= singlesCount;
                        case ">=": return bandSinglesCount >= singlesCount;
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("establishmentdate"))) {
                value = textDialog("establishmentdate");
                String operator = value.split(" ", 3)[1];
                LocalDate establishmentDate = LocalDate.parse(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    LocalDate bandEstablishmentDate = band.getCreationDate();
                    switch (operator) {
                        case "=": return bandEstablishmentDate.equals(establishmentDate);
                        case ">": return bandEstablishmentDate.isAfter(establishmentDate);
                        case "<": return bandEstablishmentDate.isBefore(establishmentDate);
                        case "<=": return bandEstablishmentDate.isBefore(establishmentDate) || bandEstablishmentDate.equals(establishmentDate);
                        case ">=": return bandEstablishmentDate.isAfter(establishmentDate) || bandEstablishmentDate.equals(establishmentDate);
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("genre"))) {
                value = textDialog("genre");
                String operator = value.split(" ", 3)[1];
                MusicGenre genre = MusicGenre.valueOf(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    MusicGenre bandGenre = band.getGenre();
                    switch (operator) {
                        case "=": return bandGenre.equals(genre);
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("album_name"))) {
                value = textDialog("album_name");
                String operator = value.split(" ", 3)[1];
                String album_name = value.split(" ", 3)[2];
                filtered = observableList.stream().filter(band -> {
                    String bandAlbum_name = band.getBestAlbum().getName();
                    switch (operator) {
                        case "=": return bandAlbum_name.equals(album_name);
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("album_tracks"))) {
                value = textDialog("album_tracks");
                String operator = value.split(" ", 3)[1];
                Long album_tracks = Long.parseLong(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    Long bandAlbum_tracks = band.getBestAlbum().getTracks();
                    switch (operator) {
                        case "=": return bandAlbum_tracks.equals(album_tracks);
                        case ">": return bandAlbum_tracks > album_tracks;
                        case "<": return bandAlbum_tracks < album_tracks;
                        case "<=": return bandAlbum_tracks <= album_tracks;
                        case ">=": return bandAlbum_tracks >= album_tracks;
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("album_length"))) {
                value = textDialog("album_length");
                String operator = value.split(" ", 3)[1];
                Long album_length = Long.parseLong(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    Long bandAlbum_length = band.getBestAlbum().getLength();
                    switch (operator) {
                        case "=": return bandAlbum_length.equals(album_length);
                        case ">": return bandAlbum_length > album_length;
                        case "<": return bandAlbum_length < album_length;
                        case "<=": return bandAlbum_length <= album_length;
                        case ">=": return bandAlbum_length >= album_length;
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            if (filterBy.equals(getStr("album_sales"))) {
                value = textDialog("album_sales");
                String operator = value.split(" ", 3)[1];
                Double album_sales = Double.parseDouble(value.split(" ", 3)[2]);
                filtered = observableList.stream().filter(band -> {
                    Double bandAlbum_sales = band.getBestAlbum().getSales();
                    switch (operator) {
                        case "=": return bandAlbum_sales.equals(album_sales);
                        case ">": return bandAlbum_sales > album_sales;
                        case "<": return bandAlbum_sales < album_sales;
                        case "<=": return bandAlbum_sales <= album_sales;
                        case ">=": return bandAlbum_sales >= album_sales;
                        default: return false;
                    }
                }).collect(Collectors.toSet());
            }
            observableList.setAll(filtered);
        });
    }
    public String getStr(String s) {
        return getResource().getString(s);
    }


    private String textDialog(String key) {
        Dialog<String> dialog = new Dialog<>();
        DialogPane pane = dialog.getDialogPane();
        dialog.setTitle(getStr(key));
        Label label = new Label(getStr(key));
        TextField textField = new TextField();
        GridPane grid = new GridPane();
        ComboBox<String> token = new ComboBox<>();
        token.setMinWidth(10);
        ObservableList<String> l = FXCollections.observableList(List.of(">", ">=", "<", "<=", "="));
        token.setValue(">");
        token.setItems(l);
        AtomicReference<String> operator = new AtomicReference<>();
        token.setOnAction(e -> {
            String value = token.getValue();
            switch (value) {
                case ">":
                    operator.set(value);
                    break;
                case "=":
                    operator.set(value);
                    break;
                case ">=":
                    operator.set(value);
                    break;
                case "<":
                    operator.set(value);
                    break;
                case "<=":
                    operator.set(value);
                    break;
            }
        });
        grid.setHgap(10);
        grid.setVgap(10);
//        grid.add(label, 0, 0);
        grid.add(label, 1, 0);
        grid.add(token, 1, 1);
        grid.add(textField, 1, 2);
        ButtonType confirm = new ButtonType(getResource().getString("confirm"), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType(getResource().getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(confirm, cancel);
        pane.setContent(grid);
        AtomicReference<String> s = new AtomicReference<>();
        dialog.setResultConverter(button -> {
            if (button == confirm) {
                s.set("id " + operator + " " + textField.getText());
            }
            return String.valueOf(s);
        });
        dialog.showAndWait();
        return String.valueOf(s);
    }

    @FXML
    private void reset(){
        if (notFilteredList != null) observableList.setAll(notFilteredList);
    }

}