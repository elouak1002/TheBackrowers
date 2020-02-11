package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class OutputController {
    @FXML private Button saveButton = new Button();
    @FXML private Button clearButton = new Button();
    @FXML private TextArea outputText = new TextArea();
    @FXML private Label fileSaved = new Label();
    private FileChooser fileChooser = new FileChooser();
    private String inputFileName;

    @FXML
    public void initialize() {
        saveButton.setDisable(true);
        clearButton.setDisable(true);
        outputText.setDisable(true);
    }

    void setInputFileName(String name) {
        inputFileName = name;
    }

    //This method allows you to choose where in you directory you would like to save your file
    @FXML
    private void saveFile() {
        String text = outputText.getText();
        Window stage = saveButton.getScene().getWindow();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("global_" + inputFileName);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveTextToFile(text, file);
        }
    }

    //Saves the file in local
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
            fileSaved.setText("Text saved as a .txt file!");
        } catch (IOException ex) {
            Logger.getLogger(OutputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setOutputText(List<String> outputStringList) {
        outputText.clear();
        for (String string : outputStringList) {
            outputText.appendText(string + "\n");
        }
        saveButton.setDisable(false);
        clearButton.setDisable(false);
        outputText.setDisable(false);
    }

    //Clear output text
    @FXML
    private void clearTextField() {
        outputText.clear();
    }
}
