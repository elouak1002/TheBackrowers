package GUI;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class OutputController {
    @FXML private Button saveButton = new Button();
    @FXML private TextArea outputText = new TextArea();
    @FXML private Label fileSaved = new Label();
    private FileChooser fileChooser = new FileChooser();
    private String inputFileName;

    @FXML
    public void initialize() {}

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
        fileChooser.setInitialFileName("updated_" + inputFileName);
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
            //write to text file
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();

            //file saved notification
            fileSaved.setText("Text has been saved!");

            //file saved notification disappears after 2 seconds
            PauseTransition visiblePause = new PauseTransition(
                    Duration.seconds(2)
            );
            visiblePause.setOnFinished(
                    event -> fileSaved.setVisible(false)
            );
            visiblePause.play();

        } catch (IOException ex) {
            //error message
            Logger.getLogger(OutputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setOutputText(List<String> outputStringList) {
        outputText.clear();
        for (String string : outputStringList) {
            outputText.appendText(string + "\n");
        }
    }

    //Clear output text
    @FXML
    private void clearTextField() {
        outputText.clear();
    }
}
