package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Root extends Application {

    //initialize panes
    private Pane currentPage;
    private Pane loadPage;
    private Pane inputPage;
    private Pane outputPage;
    private BorderPane root;

    @Override
    public void start(Stage stage) {

        //load fxml files
        FXMLLoader loadPageLoader = new FXMLLoader(getClass().getResource("Load.fxml"));
        FXMLLoader inputPageLoader = new FXMLLoader(getClass().getResource("Input.fxml"));
        FXMLLoader outputPageLoader = new FXMLLoader(getClass().getResource("Output.fxml"));

        //try-catch declaring of panes
        try {
            loadPage = loadPageLoader.load();
            inputPage = inputPageLoader.load();
            outputPage = outputPageLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //declare controllers
        LoadController loadController = loadPageLoader.getController();
        InputController inputController = inputPageLoader.getController();
        OutputController outputController = outputPageLoader.getController();

        Button previous = new Button("Previous");
        Button next = new Button("Next");

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
                    outputController.setOutputText(inputController.getOutput(loadController.getPath()));
                    outputController.setInputFileName(loadController.getPath().getFileName().toString());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Input is invalid", ButtonType.CLOSE).showAndWait();
                }
            }
        });

        BorderPane navigation = new BorderPane();
        navigation.setLeft(previous);
        navigation.setRight(next);

        root = new BorderPane();
        root.setPadding(new Insets(10,10,10,10));
        root.setBottom(navigation);
        root.setCenter(loadPage);
        currentPage = loadPage;
        previous.setDisable(true);

        //set and show scene and stage
        Scene scene = new Scene(root,1000,700);
        stage.setTitle("TheBackrowers");
        stage.setScene(scene);
        stage.show();
    }
}
