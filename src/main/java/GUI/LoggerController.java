package GUI;

import dataprocessors.Debugger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerController {
    @FXML private Button saveButton;
    @FXML private TextArea displayLog;

    @FXML
    public void initialize() {}

    void setOutputText(Debugger debugger, String filename) {
        displayLog.clear();
        if (!debugger.getLog().isEmpty()) {
            displayLog.appendText("For file '" + filename + "', the following debugging has happened:\n");
            for (String string : debugger.getLog()) {
                displayLog.appendText(string);
            }
            displayLog.appendText("\n");
        }
    }

    void setNotification(Debugger debugger) {
        if(!debugger.getLog().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Log file has been updated.", ButtonType.CLOSE).showAndWait();
        }
    }

    @FXML
    private void saveFileToDir() {
        Window stage = saveButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("logFile");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveLoggerToFile(file);
        }
    }

    private void saveLoggerToFile(File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(displayLog.getText());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LoggerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clearTextField() {
        displayLog.clear();
    }
}
