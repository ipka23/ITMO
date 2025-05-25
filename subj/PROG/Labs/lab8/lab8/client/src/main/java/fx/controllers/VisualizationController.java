package fx.controllers;

import common_entities.MusicBand;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.sql.Time;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class VisualizationController {
    public static Map<String, Color> colorsMap = Collections.synchronizedMap(new HashMap<>());

    static double LOGICAL_WIDTH = 2000;
    static double LOGICAL_HEIGHT = 2000;

    static double canvasWidth = 700;
    static double canvasHeight = 500;
    // коэффициенты масштабирования
    static double scaleX = canvasWidth / LOGICAL_WIDTH;
    static double scaleY = canvasHeight / LOGICAL_HEIGHT;

    // Центр логической системы координат (в логических единицах)
    static double logicalCenterX = LOGICAL_WIDTH / 2;
    static double logicalCenterY = LOGICAL_HEIGHT / 2;

    // Перевод логического центра в экранные координаты (пиксели)
    static double centerX = logicalCenterX * scaleX;
    static double centerY = logicalCenterY * scaleY;
    static Pane root = new Pane();

    public static Pane draw2DCoordinateSystem() {

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

    public static void drawMusicBand(double x, double y, Color color) {
        x = x * scaleX;
        y = y * scaleY;

        Circle circle = new Circle(5, color);
        circle.setLayoutX(centerX + x);
        circle.setLayoutY(centerY - y);
        root.getChildren().add(circle);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), circle);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public static void eraseMusicBand(double x, double y, Color color) {
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

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), circle);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
//        fadeTransition.setOnFinished(e -> root.getChildren().remove(circle));
        fadeTransition.play();


    }

    public static Color getColor(MusicBand band) {
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
