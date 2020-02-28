package GUI;

import dataprocessors.Debugger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerController {
    @FXML private Button saveButton = new Button();
    @FXML private TextArea displayLog = new TextArea();
    private ArrayList<String> logger;

    public LoggerController() {
        logger = new ArrayList<>();
    }

    void setOutputText(Debugger debugger) {
        displayLog.clear();
        for (Pair<String,String> nodes : debugger.getAddLog()) {
            //nodes.getValue() is nodeNameB and nodes.getKey() is nodeNameA
            logger.add(nodes.getValue() + " added to become a neighbour for " + nodes.getKey() + ".");
        }
        for (String node : debugger.getRemoveLog()) {
            logger.add(node + " has no neighbour, so it was removed.");
        }
        for (String string : logger) {
            displayLog.appendText(string + "\n");
        }
    }

    public void saveLoggerToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LoggerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void saveFileToDir() {
        String text = displayLog.getText();
        Window stage = saveButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("logFile");
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
