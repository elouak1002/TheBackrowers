package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    Parent inputPage;
    Parent outputPage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("TheBackrowers");
        inputPage = null;
        outputPage = null;
        try {
            inputPage = FXMLLoader.load(getClass().getResource("Input.fxml"));
            outputPage = FXMLLoader.load(getClass().getResource("Output.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert inputPage != null;
        assert outputPage != null;
        Scene scene = new Scene(inputPage);
        stage.setScene(scene);
        stage.show();
    }
}
