package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    private int currentPage = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("TheBackrowers");

        Parent inputPage = null;
        Parent outputPage = null;
        try {
            inputPage = FXMLLoader.load(getClass().getResource("Input.fxml"));
            outputPage = FXMLLoader.load(getClass().getResource("Output.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert inputPage != null;
        assert outputPage != null;

        ArrayList<Parent> pages = new ArrayList<>();
        pages.add(inputPage);
        pages.add(outputPage);

        BorderPane root = new BorderPane();
        BorderPane navigation = new BorderPane();

        Button previous = new Button("Previous");
        previous.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                root.setCenter(pages.get(currentPage));
            }
        });
        Button next = new Button("Next");
        next.setOnAction(event -> {
            if (currentPage < pages.size()-1) {
                currentPage++;
                root.setCenter(pages.get(currentPage));
            }
        });

        navigation.setLeft(previous);
        navigation.setRight(next);
        root.setBottom(navigation);
        root.setCenter(inputPage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
