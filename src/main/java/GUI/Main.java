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

        Parent loadPage = null;
        Parent inputPage = null;
        Parent outputPage = null;
        try {
            loadPage = FXMLLoader.load(getClass().getResource("Load.fxml"));
            inputPage = FXMLLoader.load(getClass().getResource("Input.fxml"));
            outputPage = FXMLLoader.load(getClass().getResource("Output.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert loadPage != null;
        assert inputPage != null;
        assert outputPage != null;

        ArrayList<Parent> pages = new ArrayList<>();
        pages.add(loadPage);
        pages.add(inputPage);
        pages.add(outputPage);

        BorderPane root = new BorderPane();
        BorderPane navigation = new BorderPane();

        Button previous = new Button("Previous");
        Button next = new Button("Next");
        previous.setDisable(true);
        previous.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                root.setCenter(pages.get(currentPage));
                next.setDisable(false);
            }
            if (currentPage == 0) {
                previous.setDisable(true);
            }
        });
        next.setOnAction(event -> {
            if (currentPage < pages.size()-1) {
                currentPage++;
                root.setCenter(pages.get(currentPage));
                previous.setDisable(false);
            }
            if (currentPage == pages.size()-1){
                next.setDisable(true);
            }
        });

        navigation.setLeft(previous);
        navigation.setRight(next);

        root.setBottom(navigation);
        root.setCenter(loadPage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
