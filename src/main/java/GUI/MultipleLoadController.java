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
 * Controller class for the MultipleLoad window, where user chooses multiple files.
 */
public class MultipleLoadController {

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

        //add and display the selected files into uploadTable
        if (selectFiles != null) {
            for (File selectFile : selectFiles) {
                uploadTable.getItems().add(selectFile.getAbsolutePath());
            }
        }
        //allows multiple selection of files in uploadTable and selectedTable
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
        List<File> selectedFile = event.getDragboard().getFiles();

        //add and display the selected files into listview
        if (selectedFile != null) {
            for (File file : selectedFile) {
                uploadTable.getItems().add(file.getAbsolutePath());
            }
        }

        //allows multiple selection of files in uploadTable and selectedTable
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        //testing purposes
        System.out.println("drop works");
        System.out.println(selectedFile);
    }

    /**
     * Transfer selected files from the uploadTable to selectedTable
     */
    @FXML
    private void selectFiles(){
        //initialise ArrayList
        ArrayList<String> choose = new ArrayList<>();
        //Updates any changes in listOfItems if any items get highlighted/unhighlight
        ObservableList<String> listOfItems = uploadTable.getSelectionModel().getSelectedItems();
        //assign each item to an index in an ArrayList
        for (String items : listOfItems){
            choose.add(items);
        }
        //replace selected files from uploadTable to selectedTable
        if(!choose.equals("")) {
            for(int i=0; i < choose.size(); i++) {
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
        //initialise ArrayList
        ArrayList<String> choose = new ArrayList<>();
        //Updates any changes in listOfItems if any items get highlighted/unhighlight
        ObservableList<String> listOfItems = selectedTable.getSelectionModel().getSelectedItems();
        //assign each item to an index in an ArrayList
        for (String items : listOfItems){
            choose.add(items);
        }
        //replace selected files from uploadTable to selectedTable
        if(!choose.toString().equals("")) {
            for(int i=0; i < choose.size(); i++) {
                uploadTable.getItems().addAll(choose.get(i));
                selectedTable.getItems().removeAll(choose.get(i));
                //testing purposes
                System.out.println("file got chosen : " + choose);
                System.out.println("file in original location : " + selectedTable);
            }
        }
    }
}
