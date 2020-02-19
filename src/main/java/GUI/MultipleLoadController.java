package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class MultipleLoadController {

    private Path fullPath;

    @FXML private Button uploadButton;
    @FXML private javafx.scene.control.Label selectedFileLabel;

    @FXML
    public void initialize() {}

    public Path getPath() {
        return fullPath;
    }

    @FXML
    void chooseFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        List<File> files = fileChooser.showOpenMultipleDialog(null);
        for(File printFiles : files){
            System.out.println(printFiles.getAbsolutePath());
        }
    }

    private void setLabelText(String display){
        selectedFileLabel.setText(display);
    }
}
