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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The LoggerController class displays the log window of the GUI where
 * the user can preview the log file that has been created and make
 * any changes to it before saving.
 */
public class LoggerController {
    @FXML private Button saveButton;
    @FXML private TextArea displayLog;

    @FXML
    public void initialize() {}

    /**
     * A method for outputting the contents of the @log ArrayList
     * in the Debugger class.
     * @param debugger : Gets the current debugger to access it's @Log.
     * @param filename : The current name of the file to be displayed
     *                 at the top of the @displayLog area.
     */
    void setOutputText(Debugger debugger, String filename) {
        if (!debugger.getLog().isEmpty()) {
            displayLog.appendText("For file '" + filename + "', the following debugging has happened:\n");

            for (String string : debugger.getLog()) {
                displayLog.appendText(string);
            }

            displayLog.appendText("\n");
        }
    }

    /**
     * A method for notifying the user if any changes have been
     * made to the Log file.
     * @param debugger : Gets the current debugger to access it's @Log.
     */
    void setNotification(Debugger debugger) {
        if(!debugger.getLog().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Log file has been updated.", ButtonType.CLOSE).showAndWait();
        }
    }

    /**
     * A method for saving the log page into a .txt file
     * in a directory the user wants.
     */
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

    /**
     * A method for writing the @displayLog field into a file
     * which allows the text to be saved as a .txt file in the saveFileToDir()
     * method.
     * @param file file to be saved.
     */
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

    /**
     * A method for clearing the @displayLog area where the text preview
     * is displayed.
     */
    @FXML
    private void clearTextField() {
        if (!displayLog.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear the log. Are you sure?",
                    ButtonType.YES, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                displayLog.clear();
            }
        }
    }
}
