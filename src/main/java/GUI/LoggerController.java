package GUI;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerController {
    public ArrayList<String> logger = null;
    @FXML private Button saveButton = new Button();
    @FXML public TextArea displayLog = new TextArea();
    @FXML private Button clearButton = new Button();
    public static LoggerController instance = new LoggerController();

    public static LoggerController getInstance() {
        return instance;
    }

    public LoggerController() {
        logger = new ArrayList<>();
    }

    public ArrayList<String> getList() {
        System.out.println(logger.size() + " count");
        return logger;
    }
    void setOutputText(TextArea a) {
        a.clear();
        for (String string : logger) {
            a.appendText(string + "\n");
        }
    }

    public void logAdd(String nodeNameA, String nodeNameB) {
        String neighbourAdded = nodeNameB + " added to become a neighbour for " + nodeNameA + ".";
        this.logger.add(neighbourAdded);
    }

    public void logRemove(String nodeName) {
        String removedNode = nodeName + " has no neighbour, so it was removed.";
        this.logger.add(removedNode);
    }

    public void saveLoggerToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();

            //fileSaved.setText("Text has been saved!");


        } catch (IOException ex) {
            Logger.getLogger(OutputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void saveFileToDir() {
        String text = displayLog.getText();
        Window stage = saveButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("LOGGER");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveLoggerToFile(text, file);
        }
    }

    @FXML
    private void clearTextField() {
        displayLog.clear();
    }
}
