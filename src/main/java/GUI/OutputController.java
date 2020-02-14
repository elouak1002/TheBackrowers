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


/**
 * The OutputController class displays the output window of the GUI where the user
 * can preview the .txt file and make any changes needed before saving.
 */
public class OutputController {
    //Fields
    @FXML
    private Button saveButton = new Button();
    @FXML
    private Label fileSaved = new Label();
    private FileChooser fileChooser = new FileChooser();
    private String inputFileName;

    //Text preview area.
    @FXML
    private TextArea outputText = new TextArea();

    @FXML
    public void initialize() {
    }

    /**
     * Sets the File name.
     * @param name File name.
     */
    void setInputFileName(String name) {
        inputFileName = name;
    }

    /**
     * A method for saving the preview page into a .txt file
     * in a directory the user wants.
     */
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

    /**
     * A method for writing the @outputText field into a file
     * which allows the text to be saved as a .txt file in the saveFile()
     * method. It also displays a notification about the file being saved.
     * @param content calculated text values displayed in @outputText field.
     * @param file file to be saved.
     */
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();

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
            Logger.getLogger(OutputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * A method for outputting the @outputText area with the new
     * calculated values.
     * @param outputStringList calculated values to be displayed for the user to preview.
     */
    void setOutputText(List<String> outputStringList) {
        outputText.clear();
        for (String string : outputStringList) {
            outputText.appendText(string + "\n");
        }
    }

    /**
     * A method for clearing the @outputText area where the text preview
     * is displayed.
     */
    @FXML
    private void clearTextField() {
        outputText.clear();
    }
}