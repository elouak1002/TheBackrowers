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
        stage.setTitle("TheBackrowers");
        Parent inputPage = null;
        try {
            inputPage = FXMLLoader.load(getClass().getResource("output.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert inputPage != null;
        Scene scene = new Scene(inputPage);
        //Scene scene = new Scene(new Pane(),1000,700);
        stage.setScene(scene);
        stage.show();
    }
}