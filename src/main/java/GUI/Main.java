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
//        Parent textFieldsPage = null;
//        try {
//            textFieldsPage = FXMLLoader.load(getClass().getResource("TextFieldsPage.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert textFieldsPage != null;
//        Scene scene = new Scene(textFieldsPage,1000,700);
        Scene scene = new Scene(new Pane(),1000,700);
        stage.setScene(scene);
        stage.show();
    }
}
