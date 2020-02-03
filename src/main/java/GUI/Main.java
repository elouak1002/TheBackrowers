package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Output Preview");
        Parent outputPage = null;
        try {
            outputPage = FXMLLoader.load(getClass().getResource("Output.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert outputPage != null;
        Scene scene = new Scene(outputPage);
        stage.setScene(scene);
        stage.show();
    }
}