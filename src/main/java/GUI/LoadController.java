package GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/**
* Cotroller class for the input window, where user chooses file.
*/
public class LoadController {

	private String fullPath;

	@FXML
	private Button uploadButton;

	@FXML
	private TextArea dragTarget;

	@FXML
	private Label selectedFileLabel;

	/*
    * Alters the initial state of board elements, if required.
    */
    @FXML
    private void initialize(){
        dragTarget.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = dragTarget.startDragAndDrop(TransferMode.ANY);
            }
        });

        dragTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource()!=dragTarget && event.getDragboard().hasFiles()){
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if(db.hasFiles()){
                    File file = db.getFiles().get(0);
                    if(file != null){
                        fullPath = file.getAbsolutePath();
                        setLabelText(file.getName());
                        success = true;
                    }
                }

                event.setDropCompleted(success);
                event.consume();
            }
        });

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