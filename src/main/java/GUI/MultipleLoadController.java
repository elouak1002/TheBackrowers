package GUI;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MultipleLoadController {

    private Path fullPath;

    @FXML private javafx.scene.control.Label selectedFileLabel;
    @FXML private ListView<String> uploadTable = new ListView<>();
    @FXML private ListView<String> selectedTable = new ListView<>();
    @FXML private javafx.scene.control.Button selectedFiles;


    @FXML
    public void initialize() {}

    public Path getPath() {
        return fullPath;
    }

    @FXML
    void uploadFiles() {
        //initialise fileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //allow multiple selection of files
        List<File> selectFiles = fileChooser.showOpenMultipleDialog(null);

        //this for each loop might be useless
        for(File printFiles : selectFiles){
            String showFiles = printFiles.getAbsolutePath();
            //testing purposes
            System.out.println(showFiles);

            fullPath = Paths.get(showFiles);
            setLabelText("Selected File: " + printFiles.getAbsolutePath().substring(printFiles.getAbsolutePath().lastIndexOf("\\") + 1 ));
        }

        //add and display the selected files into listview
        if (selectFiles != null) {
            for (int i = 0; i < selectFiles.size(); i++) {
                uploadTable.getItems().add(selectFiles.get(i).getAbsolutePath());
            }
        }
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

        //this for each loop might be useless
        for( File files : selectedFile){
            if(files.getAbsolutePath().contains(".txt")){
                fullPath = Paths.get(files.getAbsolutePath());
                setLabelText("Selected File: " + files.getAbsolutePath().substring(files.getAbsolutePath().lastIndexOf("\\") + 1 ));
            } else {
                setLabelText("Only .txt files allowed");
            }
        }

        //add and display the selected files into listview
        if (selectedFile != null) {
            for (int i = 0; i < selectedFile.size(); i++) {
                uploadTable.getItems().add(selectedFile.get(i).getAbsolutePath());
            }
        }
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        System.out.println(selectedFile);
    }


    private void setLabelText(String display){
        selectedFileLabel.setText(display);
    }

    @FXML
    private void selectFiles(){
        String choose = "";
        ObservableList listOfItems = uploadTable.getSelectionModel().getSelectedItems();
        for (Object items : listOfItems){
            choose +=  items;
        }
        if(choose != "") {
            selectedTable.getItems().addAll(choose);
            uploadTable.getItems().removeAll(choose);
            System.out.println("file got chosen : " + choose);
            System.out.println("file in original location : " + uploadTable);
        }
        if (!uploadTable.getItems().isEmpty()) {
            uploadTable.getSelectionModel().select(0);
        }
    }

    @FXML
    private void unselectFiles(){
        String choose = "";
        ObservableList listOfItems = selectedTable.getSelectionModel().getSelectedItems();
        for (Object items : listOfItems){
            choose +=  items;
        }
        if(choose != "") {
            uploadTable.getItems().addAll(choose);
            selectedTable.getItems().removeAll(choose);
            System.out.println("file got chosen : " + choose);
            System.out.println("file in original location : " + selectedTable);
        }
        if (!uploadTable.getItems().isEmpty()) {
            uploadTable.getSelectionModel().select(0);
        }
    }
}
