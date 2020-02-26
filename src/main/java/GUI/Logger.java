package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class Logger {
    private ArrayList<String> logger = new ArrayList<>();
    private File currentFile;
    @FXML
    private Button logButton = new Button();

    @FXML
    private TextArea displayLog = new TextArea();

    public Logger() {

    }

    public void logAdd(String nodeNameA, String nodeNameB) {
        String neighbourAdded = nodeNameB + "added to become a neighbour for " + nodeNameA + ".";
        logger.add(neighbourAdded);
    }

    public void logRemove(String nodeName) {
        String removedNode = nodeName + "has no neighbour, so it was removed.";
        logger.add(removedNode);
    }

    public void saveLoggerToFile(File file) {
        String fileName = "LogFile.txt";

        try {
            FileWriter fw = new FileWriter(fileName);
            Writer output = new BufferedWriter(fw);
            int size = logger.size();

            for (int i = 0; i < size; i++) {
                output.write(logger.get(i).toString() + "\n");
            }

            output.close();
        }
        catch (IOException e) {
            java.util.logging.Logger.getLogger(OutputController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    public void saveFileToDir() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("log file");
        Window stage = logButton.getScene().getWindow();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveLoggerToFile(file);
        }
    }

    @FXML
    public void displayLogger() {
        displayLog.clear();
        for (String string : logger) {
            displayLog.appendText(string + "\n");
        }
    }

    @FXML
    private void clearTextField() {
        displayLog.clear();
    }
}
