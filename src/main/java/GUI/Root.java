package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Root extends Application {
    private Pane loadPage;
    private Pane inputPage;
    private Pane outputPage;

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

        loadController.setInputController(inputController);
        inputController.setOutputController(outputController);

        BorderPane root = new BorderPane();
        FlowPane panes = new FlowPane(loadPage,inputPage,outputPage);
        panes.setAlignment(Pos.CENTER);
        root.setCenter(panes);
        BorderPane.setAlignment(root.getCenter(), Pos.CENTER);
        root.setStyle("-fx-background-color: aliceblue;");

        Scene scene = new Scene(root,600,900);
        stage.setScene(scene);
        stage.show();
    }
}
