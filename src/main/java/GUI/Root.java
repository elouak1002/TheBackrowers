package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Root {
    private Pane currentPage;
    private Pane loadPage;
    private Pane inputPage;
    private Pane outputPage;
    private BorderPane root;

    Root() {
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

        root = new BorderPane();
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
                    outputController.setOutputText(inputController.getOutput());
                    outputController.setInputFileName(loadController.getPath().getFileName().toString());
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
    }

    Pane getPane() {
        return root;
    }
}
