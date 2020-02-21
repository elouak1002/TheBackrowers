package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class MultipleLoadController {

    private Path fullPath;

    @FXML private Button uploadButton;
    @FXML private javafx.scene.control.Label selectedFileLabel;
    //@FXML private javafx.scene.control.ListView listView;
    @FXML
    ListView<String> listView = new ListView<String>();


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
            setLabelText("Selected File: " + printFiles.getAbsolutePath().substring(printFiles.getAbsolutePath().lastIndexOf("\\") + 1 ));


        }
        if (selectFiles != null) {
            for (int i = 0; i < selectFiles.size(); i++) {
                listView.getItems().add(selectFiles.get(i).getAbsolutePath());
            }
        }

        //listView.getItems().addAll(selectFiles);
    }

    @FXML
    private void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
            System.out.println("drag works");
        }
    }

    @FXML
    private void handleDrop(DragEvent event){
        List<File> selectedFile = event.getDragboard().getFiles();
        System.out.println("drop works");
        for( File files : selectedFile){
            if(files.getAbsolutePath().contains(".txt")){
                fullPath = Paths.get(files.getAbsolutePath());
                setLabelText("Selected File: " + files.getAbsolutePath().substring(files.getAbsolutePath().lastIndexOf("\\") + 1 ));
            } else {
                setLabelText("Only .txt files allowed");
            }
        }
        System.out.println(selectedFile);
    }


    private void setLabelText(String display){
        selectedFileLabel.setText(display);
    }
}
