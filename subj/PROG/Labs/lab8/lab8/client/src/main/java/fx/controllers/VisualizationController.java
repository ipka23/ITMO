package fx.controllers;

import common_entities.MusicBand;
import common_utility.localization.LanguageManager;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import java.util.*;

public class VisualizationController {
    public Map<String, Color> colorsMap = Collections.synchronizedMap(new HashMap<>());
    public Map<Circle, MusicBand> bandMap = Collections.synchronizedMap(new HashMap<>()); // TODO сделать чтобы старые не оставались в мапе
    double LOGICAL_WIDTH = 2000;
    double LOGICAL_HEIGHT = 2000;

    double canvasWidth = 700;
    double canvasHeight = 500;
    // коэффициенты масштабирования
    double scaleX = canvasWidth / LOGICAL_WIDTH;
    double scaleY = canvasHeight / LOGICAL_HEIGHT;

    // Центр логической системы координат (в логических единицах)
    double logicalCenterX = LOGICAL_WIDTH / 2;
    double logicalCenterY = LOGICAL_HEIGHT / 2;

    // Перевод логического центра в экранные координаты (пиксели)
    double centerX = logicalCenterX * scaleX;
    double centerY = logicalCenterY * scaleY;
    Pane root = new Pane();

    public Pane draw2DCoordinateSystem() {

        root = new Pane();


        javafx.scene.canvas.Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Получение объекта для рисования


        // Рисуем белый фон и чёрную рамку вокруг Canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvasWidth, canvasHeight);
        // оси X и Y
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeLine(0, centerY, canvasWidth, centerY);
        gc.strokeLine(centerX, 0, centerX, canvasHeight);
        gc.strokeText("x", canvasWidth - 15, centerY - 10);
        gc.strokeText("y", centerX + 10, 15);

        gc.setStroke(Color.GRAY);
        gc.strokeText("-500", centerX - 500 * scaleX - 15, centerY + 15);
        gc.strokeText("500", centerX + 500 * scaleX - 10, centerY + 15);

        gc.strokeText("500", centerX - 30, centerY - 500 * scaleY + 5);
        gc.strokeText("-500", centerX - 30, centerY + 500 * scaleY + 5);


        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        int gridStep = 100;


        for (int x = 0; x <= LOGICAL_WIDTH; x += gridStep) {
            double screenX = x * scaleX;
            gc.strokeLine(screenX, 0, screenX, canvasHeight);

        }


        for (int y = 0; y <= LOGICAL_HEIGHT; y += gridStep) {
            double screenY = y * scaleY;
            gc.strokeLine(0, screenY, canvasWidth, screenY);
        }

        root.getChildren().add(canvas);

        return root;
    }

    public void drawMusicBand(MusicBand band) {
        double x = band.getCoordinates().getX();
        double y = band.getCoordinates().getY();
        Color color = this.getColor(band);
        x = x * scaleX;
        y = y * scaleY;

        Circle circle = new Circle(10, color);
        circle.setLayoutX(centerX + x);
        circle.setLayoutY(centerY - y);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.5);
        root.getChildren().add(circle);
        bandMap.put(circle, band);
        FadeTransition transition = new FadeTransition(Duration.seconds(3), circle);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
        setCircleEvents(circle, band);
    }
    public void setCircleEvents(Circle circle, MusicBand band) {
        circle.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Image icon = new Image("images/info.png");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icon);

            alert.setTitle(null);
            StringBuilder s = new StringBuilder();
            s.append(getResource("id")).append(": ").append(band.getId()).append("\n");
            s.append(getResource("owner")).append(": ").append(band.getOwner()).append("\n");
            s.append(getResource("name")).append(": ").append(band.getName()).append("\n");
            s.append(getResource("coordinates_x")).append(": ").append(band.getCoordinates().getX()).append("\n");
            s.append(getResource("coordinates_y")).append(": ").append(band.getCoordinates().getY()).append("\n");
            s.append(getResource("creationdate")).append(": ").append(band.getCreationDate()).append("\n");
            s.append(getResource("numberofparticipants")).append(": ").append(band.getNumberOfParticipants()).append("\n");
            s.append(getResource("establishmentdate")).append(": ").append(band.getEstablishmentDate()).append("\n");
            s.append(getResource("genre")).append(": ").append(band.getGenre()).append("\n");
            s.append(getResource("album_name")).append(": ").append(band.getBestAlbum().getName()).append("\n");
            s.append(getResource("album_tracks")).append(": ").append(band.getBestAlbum().getTracks()).append("\n");
            s.append(getResource("album_length")).append(": ").append(band.getBestAlbum().getLength()).append("\n");
            s.append(getResource("album_sales")).append(": ").append(band.getBestAlbum().getSales()).append("\n");
//                    alert.setHeaderText(s.toString());
            alert.setHeaderText(getResource("musicBandInfo"));
            alert.setContentText(s.toString());
            alert.showAndWait();
        });
        circle.setOnMouseEntered(e -> {
            Tooltip tooltip = new Tooltip(getResource("id") + ": " + bandMap.get(circle).getId());
            Tooltip.install(circle, tooltip);
        });
    }


    public void eraseMusicBand(MusicBand band, Color color) {
        double x = band.getCoordinates().getX();
        double y = band.getCoordinates().getY();
        x = x * scaleX;
        y = y * scaleY;
        double layoutX = centerX + x;
        double layoutY = centerY - y;

        Circle circle = null;
        for (Node node : root.getChildren()) {
            if (node instanceof Circle) {
                circle = (Circle) node;
                if (circle.getFill().equals(color)) {
                    if ((Math.abs(circle.getLayoutX() - layoutX) < 1e-3 && Math.abs(circle.getLayoutY() - layoutY) < 1e-3)) {
                        break;
                    }
                }
            }
        }
        colorsMap.remove(band.getOwner(), color);

        FadeTransition transition = new FadeTransition(Duration.seconds(3), circle);
        transition.setFromValue(1);
        transition.setToValue(0);
        Circle finalCircle = circle;
        transition.setOnFinished(e -> root.getChildren().remove(finalCircle));
        transition.play();
    }
    public String getResource(String key) {
        return LanguageManager.getBundle().getString(key);
    }


    public Color getColor(MusicBand band) {
        if (colorsMap.containsKey(band.getOwner())) {
            return colorsMap.get(band.getOwner());
        } else {
//            Color color = Color.color(Math.random(), Math.random(), Math.random());
            Color color = null;
            if (!colorsMap.containsValue(Color.GREEN)) {
                color = Color.GREEN;
            } else if (!colorsMap.containsValue(Color.RED)) {
                color = Color.RED;
            } else if (!colorsMap.containsValue(Color.BLUE)) {
                color = Color.BLUE;
            } else if (!colorsMap.containsValue(Color.PINK)) {
                color = Color.PINK;
            }
            colorsMap.put(band.getOwner(), color);
            return color;
        }
    }
}
