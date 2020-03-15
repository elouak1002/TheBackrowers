package GUI;

import dataprocessors.XMLDebugger;
import dataprocessors.Debugger;
import dataprocessors.XMLCreator;
import datastructures.Node;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import parser.Parser;
import parser.XMLParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Controller class for the XMLGenerator window, where user chooses multiple text files to merge into a xml file.
 */
public class XMLGeneratorController {
    @FXML private ListView<String> uploadTable = new ListView<>();
    @FXML private ListView<String> selectedTable = new ListView<>();
    @FXML private Button saveButton;
    @FXML private Label saveNotification;
    @FXML private VBox xmlGeneratorRoot;
    private Debugger debugger;
    private LoggerController loggerController;
    private String debuggedFileNames;

    /**
     * Initialize functions when page starts up
     */
    @FXML
    public void initialize() {
        saveButton.setDisable(true);
        xmlGeneratorRoot.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                removeHighlightedFiles();
            }
        });
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        saveNotification.setVisible(false);
    }

    /**
     * Sets the logger controller
     * @param loggerController - the LoggerController as set from Root
     */
    void setLoggerController(LoggerController loggerController) {
        this.loggerController = loggerController;
    }

    boolean selectedTableIsNotEmpty() {
        return !selectedTable.getItems().isEmpty();
    }

    Debugger getDebugger() {
        return debugger;
    }

    String getDebuggedFileNames() {
        return debuggedFileNames;
    }

    /**
     * Upload multiple files via a file chooser
     */
    @FXML
    void uploadFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        List<File> selectFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectFiles != null) {
            for (File selectFile : selectFiles) {
                boolean itemInUploadTable = uploadTable.getItems().contains(selectFile.getAbsolutePath());
                boolean itemInSelectedTable = selectedTable.getItems().contains(selectFile.getAbsolutePath());
                if (!itemInUploadTable && !itemInSelectedTable) {
                    uploadTable.getItems().add(selectFile.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Merges and debugs the selected files' lines and formats them into XML.
     * @return the merged string in XML form
     */
    List<String> getXMLStringList() {
        XMLParser parser = new XMLParser(selectedTable.getItems());
        try {
            debugger = new XMLDebugger(parser.createNodes());
            setDebuggedFileNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeMap<String, Node> nodeMap = new TreeMap<>(debugger.getMap());
        ArrayList<String> nodeOrder = new ArrayList<>(nodeMap.keySet());
        XMLCreator xmlCreator = new XMLCreator(nodeMap,nodeOrder);
        return xmlCreator.createXMLFile();
    }

    private void setDebuggedFileNames() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String path : selectedTable.getItems()) {
            Debugger individualDebugger = new XMLDebugger(new Parser(Paths.get(path)).createNodes());
            if (!individualDebugger.getLog().isEmpty()) {
                stringBuilder.append(Paths.get(path).getFileName().toString()).append("+");
            }
        }
        if (stringBuilder.length() > 0) stringBuilder.deleteCharAt(stringBuilder.length()-1);
        debuggedFileNames = stringBuilder.toString();
    }

    /**
     * Save xml file via a file chooser
     */
    @FXML
    void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("merged_xml");
        File file = fileChooser.showSaveDialog(saveButton.getScene().getWindow());

        if (file != null) {
            try {
                PrintWriter writer = new PrintWriter(file);
                StringBuilder stringBuilder = new StringBuilder();
                for (String string : getXMLStringList()) {
                    stringBuilder.append(string).append("\n");
                }
                writer.println(stringBuilder.toString());
                writer.close();

                saveNotification.setText("File has been saved!");
                saveNotification.setVisible(true);
                PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
                visiblePause.setOnFinished(event -> saveNotification.setVisible(false));
                visiblePause.play();

                loggerController.setNotification(debugger);
                loggerController.setOutputText(debugger,file.getName());
            } catch (IOException e) {
                e.printStackTrace();
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
        }
    }

    /**
     * Drop event. Allow dropping of files into UploadTable
     */
    @FXML
    private void handleDrop(DragEvent event){
        List<File> toBeUploaded = event.getDragboard().getFiles();
        if (toBeUploaded != null) {
            for (File file : toBeUploaded) {
                boolean itemInUploadTable = uploadTable.getItems().contains(file.getAbsolutePath());
                boolean itemInSelectedTable = selectedTable.getItems().contains(file.getAbsolutePath());
                if (!itemInUploadTable && !itemInSelectedTable) {
                    uploadTable.getItems().add(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Transfer selected files from the uploadTable to selectedTable
     */
    @FXML
    private void selectFiles(){
        ObservableList<String> listOfFiles = uploadTable.getSelectionModel().getSelectedItems();
        ArrayList<String> chosenFiles = new ArrayList<>(listOfFiles);
        for (String chosenFile : chosenFiles) {
            if (!(chosenFile == null)) {
                selectedTable.getItems().addAll(chosenFile);
                uploadTable.getItems().removeAll(chosenFile);
            }
        }
        if(!selectedTable.getItems().isEmpty()){
            saveButton.setDisable(false);
        }
    }

    /**
     * Transfer selected files from the uploadTable to selectedTable
     */
    @FXML
    private void unselectFiles(){
        ObservableList<String> listOfFiles = selectedTable.getSelectionModel().getSelectedItems();
        ArrayList<String> chosenFiles = new ArrayList<>(listOfFiles);
        for (String chosenFile : chosenFiles) {
            if (!(chosenFile == null)) {
                uploadTable.getItems().addAll(chosenFile);
                selectedTable.getItems().removeAll(chosenFile);
            }
        }
        if(selectedTable.getItems().isEmpty()){
           saveButton.setDisable(true);
        }
    }

    /**
     * Remove highlighted files from uploadTable
     */
    @FXML
    void removeHighlightedFiles(){
        ObservableList<String> listOfFiles = uploadTable.getSelectionModel().getSelectedItems();
        ArrayList<String> chosenFiles = new ArrayList<>(listOfFiles);
        for (String file : chosenFiles) {
            uploadTable.getItems().removeAll(file);
        }
    }

    /**
     * Remove all files from uploadTable and selectedTable
     */
    @FXML
    void clearAllFiles(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Clear All Files?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            uploadTable.getItems().clear();
            selectedTable.getItems().clear();
        }
    }
}
