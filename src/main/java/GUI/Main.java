package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("TheBackrowers");
        Scene scene = new Scene(new Root().getPane());
        stage.setScene(scene);
        stage.show();
    }
}
