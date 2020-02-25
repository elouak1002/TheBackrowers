package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller class for the XMLGenerator window, where user chooses multiple files.
 */
public class XMLGeneratorController {

    @FXML private ListView<String> uploadTable = new ListView<>();
    @FXML private ListView<String> selectedTable = new ListView<>();

    private Path fullPath;

    @FXML
    public void initialize() {}

    /**
     * Returns absolute path of file
     */
    public Path getPath() {
        return fullPath;
    }

    /**
     * Upload multiple files via a file chooser
     */
    @FXML
    void uploadFiles() {
        //initialise fileChooser
        FileChooser fileChooser = new FileChooser();
        //set specific extensions for fileChooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //allow multiple selection of files
        List<File> selectFiles = fileChooser.showOpenMultipleDialog(null);
        //allows multiple selection of files in uploadTable and selectedTable
        multipleSelection();
        //add and display the selected files into uploadTable
        if (selectFiles != null) {
            for (File selectFile : selectFiles) {
                if (!uploadTable.getItems().contains(selectFile.getAbsolutePath()) && !selectedTable.getItems().contains(selectFile.getAbsolutePath())) {
                    uploadTable.getItems().add(selectFile.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Drag event. Allow dragging of files into uploadTable
     */
    @FXML
    private void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
            //testing purposes
            System.out.println("drag works");
        }
    }

    /**
     * Drop event. Allow dropping of files into UploadTable
     */
    @FXML
    private void handleDrop(DragEvent event){
        //receive files when dropped
        List<File> selectedFiles = event.getDragboard().getFiles();
        //allows multiple selection of files in uploadTable and selectedTable
        multipleSelection();
        //add and display the selected files into listview
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                if (!uploadTable.getItems().contains(file.getAbsolutePath()) && !selectedTable.getItems().contains(file.getAbsolutePath())) {
                    uploadTable.getItems().add(file.getAbsolutePath());
                }
            }
        }
        //testing purposes
        System.out.println("drop works");
        System.out.println(selectedFiles);
    }

    /**
     * Allows multiple selection of files in uploadTable and selectedTable
     */
    private void multipleSelection(){
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Transfer selected files from the uploadTable to selectedTable
     */
    @FXML
    private void selectFiles(){
        //Updates any changes in listOfItems if any items get highlighted/unhighlight
        ObservableList<String> listOfItems = uploadTable.getSelectionModel().getSelectedItems();
        //initialise ArrayList
        ArrayList<String> choose = new ArrayList<>(listOfItems);
        //replace selected files from uploadTable to selectedTable
        for(int i=0; i < choose.size(); i++) {
            if(!(choose.get(i) == null)) {
                selectedTable.getItems().addAll(choose.get(i));
                uploadTable.getItems().removeAll(choose.get(i));
                //testing purposes
                System.out.println("file got chosen : " + choose);
                System.out.println("file in original location : " + uploadTable);
            }
        }
    }

    /**
     * Transfer selected files from the uploadTable to selectedTable
     */
    @FXML
    private void unselectFiles(){
        //Updates any changes in listOfItems if any items get highlighted/unhighlight
        ObservableList<String> listOfItems = selectedTable.getSelectionModel().getSelectedItems();
        //initialise ArrayList
        ArrayList<String> choose = new ArrayList<>(listOfItems);
        //replace selected files from uploadTable to selectedTable
        for(int i=0; i < choose.size(); i++) {
            if(!(choose.get(i) == null)) {
                uploadTable.getItems().addAll(choose.get(i));
                selectedTable.getItems().removeAll(choose.get(i));
                //testing purposes
                System.out.println("file got chosen : " + choose);
                System.out.println("file in original location : " + selectedTable);
            }
        }
    }
}
