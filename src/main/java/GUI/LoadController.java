package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import java.io.*;
import java.nio.file.Path;

/**
* Controller class for the input window, where user chooses file.
*/
public class LoadController {
	@FXML private Button uploadButton;
	@FXML private Label selectedFileLabel;
	private Path fullPath;
	private InputController inputController;

    @FXML
    public void initialize() {}

    @FXML
    private void chooseFile(){
    	Window stage = uploadButton.getScene().getWindow();

    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Pick a file...");
    	fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null ){
            fullPath = selectedFile.toPath();
            setLabelText("Selected File: " + selectedFile.getName());
            inputController.enableInput();
            inputController.setNodes(fullPath);
        }
    }

    private void setLabelText(String display){
    	selectedFileLabel.setText(display);
    }

    @FXML
    private void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void handleDrop(DragEvent event){
        File selectedFile = event.getDragboard().getFiles().get(0);
        if(selectedFile != null ){
            if(selectedFile.getName().endsWith(".txt")){
                fullPath = selectedFile.toPath();
                setLabelText("Selected File: " + selectedFile.getName());
                inputController.enableInput();
                inputController.setNodes(fullPath);
            } else {
                setLabelText("Only .txt files allowed");
            }
        }
    }

    void setInputController(InputController inputController) {
        this.inputController = inputController;
    }
}
