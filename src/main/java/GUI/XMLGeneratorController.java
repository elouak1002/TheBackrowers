package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller class for the XMLGenerator window, where user chooses multiple text files to merge into a xml file.
 */
public class XMLGeneratorController {

    @FXML private ListView<String> uploadTable = new ListView<>();
    @FXML private ListView<String> selectedTable = new ListView<>();
    @FXML private Button saveButton;
    @FXML private Button removeFileButton;
    @FXML private Button clearAllButton;

    private Path fullPath;

    @FXML
    public void initialize() {
        saveButton.setDisable(true);
    }

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
        //allow multiple selection of files in fileChooser
        List<File> selectFiles = fileChooser.showOpenMultipleDialog(null);
        //allows multiple selection of files in uploadTable and selectedTable
        multipleSelection();
        //add and display the selected files into uploadTable
        if (selectFiles != null) {
            for (File selectFile : selectFiles) {
                //condition to check if selectFile is in uploadTable && selectedTable
                boolean itemInUploadTable = uploadTable.getItems().contains(selectFile.getAbsolutePath());
                boolean itemInSelectedTable = selectedTable.getItems().contains(selectFile.getAbsolutePath());
                if (!itemInUploadTable && !itemInSelectedTable) {
                    //get file name from file path
                    String getFileName = selectFile.getAbsolutePath().substring(selectFile.getAbsolutePath().lastIndexOf("\\") + 1);
                    uploadTable.getItems().add(getFileName);
                }
            }
        }
    }

//    /**
//     * todo: Merging of text files into an xml file
//     */
//    void mergeFiles(String filePath){
//
//    }

    /**
     * Save xml file via a file chooser
     */
    @FXML
    void saveFile() {
        //calls the merge process
        //mergeFiles();
        //initialise fileChooser
        FileChooser fileChooser = new FileChooser();
        //set specific extensions for fileChooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        //set default save directory to user home
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("merged_xml");
        File file = fileChooser.showSaveDialog(null);
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
                //condition to check if selectFile is in uploadTable && selectedTable
                boolean itemInUploadTable = uploadTable.getItems().contains(file.getAbsolutePath());
                boolean itemInSelectedTable = selectedTable.getItems().contains(file.getAbsolutePath());
                if (!itemInUploadTable && !itemInSelectedTable) {
                    //get file name from file path
                    String getFileName = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\") + 1);
                    uploadTable.getItems().add(getFileName);
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
                System.out.println("file in original location : " + uploadTable.getId());
            }
        }
        //enable merge-save function if selectedTable is not empty
        if(!selectedTable.getItems().isEmpty()){
            saveButton.setDisable(false);
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
                System.out.println("file in original location : " + selectedTable.getId());
            }
        }
        //disable merge-save function if selectedTable is empty
        if(selectedTable.getItems().isEmpty()){
           saveButton.setDisable(true);
        }
    }

    /**
     * Remove highlighted files from uploadTable
     */
    @FXML
    void removeFiles(){
        //Updates any changes in listOfItems if any items get highlighted/unhighlight
        ObservableList<String> listOfItems = uploadTable.getSelectionModel().getSelectedItems();
        //initialise ArrayList
        ArrayList<String> choose = new ArrayList<>(listOfItems);
        //remove highlighted files from uploadTable
        for(int i=0; i < choose.size(); i++) {
            uploadTable.getItems().removeAll(choose.get(i));
            //testing purposes
            System.out.println(choose.get(i) + " has been removed");
        }
    }

    /**
     * Remove all files from uploadTable and selectedTable
     */
    @FXML
    void clearAllFiles(){
        //provide confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Clear All Files?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        //clear all information in both tables if "Yes" button is selected
        if (alert.getResult() == ButtonType.YES) {
            uploadTable.getItems().clear();
            selectedTable.getItems().clear();
        }
    }
}
