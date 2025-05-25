package fx.controllers;

import common_entities.MusicBand;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VisualizationController {
    public static Map<String, Color> colorsMap = Collections.synchronizedMap(new HashMap<>());

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

    public void drawMusicBand(double x, double y, Color color) {

        x = x * scaleX;
        y = y * scaleY;

        Circle circle = new Circle(5, color);
        circle.setLayoutX(centerX + x);
        circle.setLayoutY(centerY - y);
        root.getChildren().add(circle);
    }

    public Color getColor(MusicBand band) {
        if (colorsMap.containsKey(band.getOwner())) {
            return colorsMap.get(band.getOwner());
        } else {
            Color color = Color.color(Math.random(), Math.random(), Math.random());
            colorsMap.put(band.getOwner(), color);
            return color;
        }
    }
}
