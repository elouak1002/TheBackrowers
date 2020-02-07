package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    private int currentPage = 0;
    private Pane loadPage = null;
    private Pane inputPage = null;
    private Pane outputPage = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("TheBackrowers");

        FXMLLoader loadPageLoader = new FXMLLoader(getClass().getResource("Load.fxml"));
        FXMLLoader inputPageLoader = new FXMLLoader(getClass().getResource("Input.fxml"));
        FXMLLoader outputPageLoader = new FXMLLoader(getClass().getResource("Output.fxml"));
        try {
            loadPage = loadPageLoader.load();
            inputPage = inputPageLoader.load();
            outputPage = outputPageLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert loadPage != null;
        assert inputPage != null;
        assert outputPage != null;

        LoadController loadController = loadPageLoader.getController();
        InputController inputController = inputPageLoader.getController();
        OutputController outputController = outputPageLoader.getController();

        ArrayList<Pane> pages = new ArrayList<>();
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
                if (loadController.getPath() != null) {
                    if (pages.get(currentPage) == inputPage) {
                        inputController.setNodes(loadController.getPath());
                    } else if (pages.get(currentPage) == outputPage) {
                        inputController.inputToOutput(outputController);
                    }
                }
            }
            if (currentPage == pages.size()-1){
                next.setDisable(true);
            }
        });

        navigation.setLeft(previous);
        navigation.setRight(next);

        root.setBottom(navigation);
        root.setCenter(loadPage);
        root.setStyle("-fx-background-color: aliceblue;");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
