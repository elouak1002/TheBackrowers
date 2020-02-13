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

    //initialize panes
    private Pane loadPage;
    private Pane inputPage;
    private Pane outputPage;

    @Override
    public void start(Stage stage) {
        //set stage title
        stage.setTitle("TheBackrowers");

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

        loadController.setInputController(inputController);
        inputController.setOutputController(outputController);

        //insert fxml panes into structured containers
        BorderPane root = new BorderPane();
        FlowPane panes = new FlowPane(loadPage,inputPage,outputPage);
        panes.setVgap(2);
        panes.setAlignment(Pos.CENTER);
        root.setCenter(panes);
        BorderPane.setAlignment(root.getCenter(), Pos.CENTER);
        root.setStyle("-fx-background-color: #DDDDDD ;");

        //show stage
        Scene scene = new Scene(root,620,780);
        stage.setScene(scene);
        stage.show();
    }
}
