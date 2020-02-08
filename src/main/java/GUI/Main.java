package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Pane currentPage;
    private Pane loadPage;
    private Pane inputPage;
    private Pane outputPage;

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
        LoadController loadController = loadPageLoader.getController();
        InputController inputController = inputPageLoader.getController();
        OutputController outputController = outputPageLoader.getController();

        BorderPane root = new BorderPane();
        BorderPane navigation = new BorderPane();

        Button previous = new Button("Previous");
        Button next = new Button("Next");
        currentPage = loadPage;
        previous.setDisable(true);

        previous.setOnAction(event -> {
            if (currentPage == inputPage) {
                currentPage = loadPage;
                root.setCenter(loadPage);
                previous.setDisable(true);
            } else if (currentPage == outputPage) {
                currentPage = inputPage;
                root.setCenter(inputPage);
                next.setDisable(false);
            }
        });
        next.setOnAction(event -> {
            if (currentPage == loadPage) {
                if (loadController.getPath() != null) {
                    currentPage = inputPage;
                    root.setCenter(inputPage);
                    previous.setDisable(false);
                    inputController.setNodes(loadController.getPath());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please select file", ButtonType.CLOSE).showAndWait();
                }
            } else if (currentPage == inputPage) {
                if (inputController.inputIsValid()) {
                    currentPage = outputPage;
                    root.setCenter(outputPage);
                    next.setDisable(true);
                    inputController.inputToOutput(outputController);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Input is invalid", ButtonType.CLOSE).showAndWait();
                }
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
