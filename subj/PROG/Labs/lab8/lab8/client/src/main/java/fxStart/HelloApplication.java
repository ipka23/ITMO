package fxStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Title");
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello.fxml")))));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}