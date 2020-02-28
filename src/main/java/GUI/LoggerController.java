package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

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
    void test(TextArea a) {
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

    public void saveLoggerToFile(File file) {
        String fileName = "LogFile.txt";

        try {
            FileWriter fw = new FileWriter(fileName);
            Writer output = new BufferedWriter(fw);
            int size = logger.size();

            for (int i = 0; i < size; i++) {
                output.write(logger.get(i) + "\n");
            }

            output.close();
        }
        catch (IOException e) {
            java.util.logging.Logger.getLogger(LoggerController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    public void saveFileToDir() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("log file");
        Window stage = saveButton.getScene().getWindow();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveLoggerToFile(file);
        }
    }

    @FXML
    private void clearTextField() {
        displayLog.clear();
    }
}
