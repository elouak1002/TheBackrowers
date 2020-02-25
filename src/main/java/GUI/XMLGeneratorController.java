package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller class for the XMLGenerator window, where user chooses multiple text files to merge into a xml file.
 */
public class XMLGeneratorController {

    @FXML private ListView<String> uploadTable = new ListView<>();
    @FXML private ListView<String> selectedTable = new ListView<>();
    @FXML private Button saveButton;
    @FXML private VBox xmlGeneratorRoot;

    /**
     * initialize functions when page starts up
     */
    @FXML
    public void initialize() {
        //disable merge-save function
        saveButton.setDisable(true);
        //set delete key to delete files
        xmlGeneratorRoot.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                removeHighlightedFiles();
            }
        });
        //allow multiple selection of files in uploadTable and selectedTable
        multipleSelection();
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
        List<File> toBeUploaded = event.getDragboard().getFiles();
        //add and display the selected files into listview
        if (toBeUploaded != null) {
            for (File file : toBeUploaded) {
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
        System.out.println(toBeUploaded);
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
        ObservableList<String> listOfFiles = uploadTable.getSelectionModel().getSelectedItems();
        //initialise ArrayList
        ArrayList<String> chosenFiles = new ArrayList<>(listOfFiles);
        //replace selected files from uploadTable to selectedTable
        for(int i=0; i < chosenFiles.size(); i++) {
            if(!(chosenFiles.get(i) == null)) {
                selectedTable.getItems().addAll(chosenFiles.get(i));
                uploadTable.getItems().removeAll(chosenFiles.get(i));
                //testing purposes
                System.out.println("file got chosen : " + chosenFiles);
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
        ObservableList<String> listOfFiles = selectedTable.getSelectionModel().getSelectedItems();
        //initialise ArrayList
        ArrayList<String> chosenFiles = new ArrayList<>(listOfFiles);
        //replace selected files from uploadTable to selectedTable
        for(int i=0; i < chosenFiles.size(); i++) {
            if(!(chosenFiles.get(i) == null)) {
                uploadTable.getItems().addAll(chosenFiles.get(i));
                selectedTable.getItems().removeAll(chosenFiles.get(i));
                //testing purposes
                System.out.println("file got chosen : " + chosenFiles);
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
    void removeHighlightedFiles(){
        //Updates any changes in listOfItems if any items get highlighted/unhighlight
        ObservableList<String> listOfFiles = uploadTable.getSelectionModel().getSelectedItems();
        //initialise ArrayList
        ArrayList<String> chosenFiles = new ArrayList<>(listOfFiles);
        //remove highlighted files from uploadTable
        for (String file : chosenFiles) {
            uploadTable.getItems().removeAll(file);
            //testing purposes
            System.out.println(file + " has been removed");
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
