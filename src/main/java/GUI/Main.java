package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("TheBackrowers");
        Parent inputPage = null;
        try {
            inputPage = FXMLLoader.load(getClass().getClassLoader().getResource("Load.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert inputPage != null;
        Scene scene = new Scene(inputPage);
        stage.setScene(scene);
        stage.show();
    }
}
