package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Root extends Application {
    //declare panes
    private Pane currentPage;
    private Pane homePage;
    private Pane loadPage;
    private Pane inputPage;
    private Pane outputPage;
    private Pane xmlGeneratorPage;
    private Pane loggerPage;
    private BorderPane root;

    @Override
    public void start(Stage stage) {
        //initialise fxml loaders
        FXMLLoader homePageLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        FXMLLoader loadPageLoader = new FXMLLoader(getClass().getResource("Load.fxml"));
        FXMLLoader inputPageLoader = new FXMLLoader(getClass().getResource("Input.fxml"));
        FXMLLoader outputPageLoader = new FXMLLoader(getClass().getResource("Output.fxml"));
        FXMLLoader xmlGeneratorLoader = new FXMLLoader(getClass().getResource("XMLGenerator.fxml"));
        FXMLLoader loggerLoader = new FXMLLoader(getClass().getResource("logger.fxml"));
        //try-catch assignment of panes
        try {
            homePage = homePageLoader.load();
            loadPage = loadPageLoader.load();
            inputPage = inputPageLoader.load();
            outputPage = outputPageLoader.load();
            xmlGeneratorPage = xmlGeneratorLoader.load();
            loggerPage = loggerLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //initialise controllers
        HomeController homeController = homePageLoader.getController();
        LoadController loadController = loadPageLoader.getController();
        InputController inputController = inputPageLoader.getController();
        OutputController outputController = outputPageLoader.getController();
        XMLGeneratorController xmlGeneratorController = xmlGeneratorLoader.getController();
        LoggerController loggerController = loggerLoader.getController();

        Button previous = new Button("Previous");
        Button next = new Button("Next");
        Button log = new Button("Log");

        //assign functionality to homeController buttons
        homeController.getWranglerButton().setOnAction(event -> {
            root.setCenter(loadPage);
            currentPage = loadPage;
            previous.setDisable(false);
            next.setDisable(false);
        });
        homeController.getXMLButton().setOnAction(event -> {
            root.setCenter(xmlGeneratorPage);
            currentPage = xmlGeneratorPage;
            previous.setDisable(false);
            next.setDisable(true);
        });

        previous.setOnAction(event -> {
            //Data Wrangler
            if (currentPage == loadPage) {
                root.setCenter(homePage);
                currentPage = homePage;
                previous.setDisable(true);
                next.setDisable(true);
                log.setDisable(false);
            } else if (currentPage == inputPage) {
                root.setCenter(loadPage);
                currentPage = loadPage;
                log.setDisable(false);
            } else if (currentPage == outputPage) {
                root.setCenter(inputPage);
                currentPage = inputPage;
                next.setDisable(false);
                log.setDisable(false);
            }
            //XML Generator
            if (currentPage == xmlGeneratorPage) {
                root.setCenter(homePage);
                currentPage = homePage;
                log.setDisable(false);
                previous.setDisable(true);
            }

            if(currentPage == loggerPage) {
                root.setCenter(outputPage);
                currentPage = outputPage;
                next.setDisable(true);
                log.setDisable(false);
            }
        });
        next.setOnAction(event -> {
            if (currentPage == loadPage) {
                if (loadController.getPath() != null) {
                    root.setCenter(inputPage);
                    currentPage = inputPage;
                    log.setDisable(false);
                    inputController.setNodes(loadController.getPath());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please select file", ButtonType.CLOSE).showAndWait();
                }
            } else if (currentPage == inputPage) {
                if (inputController.inputIsValid()) {
                    root.setCenter(outputPage);
                    currentPage = outputPage;
                    next.setDisable(true);
                    log.setDisable(false);
                    outputController.setOutputText(inputController.getOutput(loadController.getPath()));
                    outputController.setInputFileName(loadController.getPath().getFileName().toString());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Input is invalid", ButtonType.CLOSE).showAndWait();
                }
            }
        });

        log.setOnAction(event -> {
            root.setCenter(loggerPage);
            currentPage = loggerPage;
            log.setDisable(true);
            previous.setDisable(false);
            loggerController.displayLogger();
        });



        BorderPane navigation = new BorderPane();
        navigation.setLeft(previous);
        navigation.setRight(next);
        navigation.setCenter(log);

        root = new BorderPane();
        root.setPadding(new Insets(10,10,10,10));
        root.getStylesheets().add("Styling/GUI.css");
        root.setBottom(navigation);
        root.setCenter(homePage);
        currentPage = homePage;
        previous.setDisable(true);
        next.setDisable(true);

        //set and show scene and stage
        Scene scene = new Scene(root,960,640);
        stage.setTitle("TheBackrowers");
        stage.getIcons().add(new Image("Styling/Logo.PNG"));
        stage.setScene(scene);
        stage.show();
    }
}
