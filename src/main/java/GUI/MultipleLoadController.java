package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MultipleLoadController {

    private Path fullPath;

    @FXML private Button uploadButton;
    @FXML private javafx.scene.control.Label selectedFileLabel;
    @FXML private javafx.scene.control.ListView listView;


    @FXML
    public void initialize() {}

    public Path getPath() {
        return fullPath;
    }

    @FXML
    void chooseFiles() {
        //initialise fileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //allow multiple selection of files
        List<File> selectFiles = fileChooser.showOpenMultipleDialog(null);

        for(File printFiles : selectFiles){
            String showFiles = printFiles.getAbsolutePath();
            //testing purposes
            System.out.println(showFiles);

            fullPath = Paths.get(showFiles);
            //listView.getItems().addAll(showFiles);
            setLabelText("Selected File: " + showFiles);
        }
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
                System.out.println(selectedFile.getName());
            } else {
                setLabelText("Only .txt files allowed");
            }
        }
    }


    private void setLabelText(String display){
        selectedFileLabel.setText(display);
    }
}
