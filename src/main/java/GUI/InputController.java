package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/**
* Cotroller class for the input window, where user chooses file.
*/
public class InputController {

	private String fullPath;

	@FXML
	private Button uploadButton;

	@FXML
	private TextArea dropArea;

	@FXML
	private Label selectedFileLabel;

	/*
    * Alters the initial state of board elements, if required.
    */
    @FXML
    private void initialize(){
    }

    public String getPath() {
    	return fullPath;
    }

    @FXML
    private void chooseFile(){

    	Window stage = uploadButton.getScene().getWindow();

    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Pick a file...");
    	fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null ){
            fullPath = selectedFile.getAbsolutePath();
            setLabelText(selectedFile.getName());
        }
    }
    

    private void setLabelText(String fileName){
    	selectedFileLabel.setText("selected File: " + fileName);
    }


    

}