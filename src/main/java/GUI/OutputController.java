package GUI;

import ALG.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class OutputController {
    @FXML private Button saveButton = new Button();
    @FXML private TextArea outputText = new TextArea();
    @FXML private Label fileSaved = new Label();
    private FileChooser fileChooser = new FileChooser();

    @FXML
    public void initialize() {}

    //This method allows you to choose where in you directory you would like to save your file
    @FXML
    private void saveFile() {
        String text = outputText.getText();
        Window stage = saveButton.getScene().getWindow();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("save");
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

    void setOutputText(Parser parser) {
        outputText.clear();
        outputText.appendText("Testing Output\n\n");
        try {
            for (String string : parser.getAllLines()) {
                outputText.appendText(string + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Clear output text
    @FXML
    private void clearTextField() {
        outputText.clear();
    }
}
