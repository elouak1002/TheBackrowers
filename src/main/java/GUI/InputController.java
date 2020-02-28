package GUI;

import datastructures.*;
import dataprocessors.*;
import parser.*;
import linecreators.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * The InputController class allows the user to input the various numbers needed to wrangle the data,
 * along with choosing the reference node passed from the parsed input file from a drop down menu.
 */
public class InputController {
    @FXML private ChoiceBox<String> referenceNodeChoiceBox;
    @FXML private TextField rotationAngle;
    @FXML private TextField scaleFactorX;
    @FXML private TextField scaleFactorY;
    @FXML private TextField finalPositionX;
    @FXML private TextField finalPositionY;
    private TreeMap<String,Node> nodes;

    @FXML
    public void initialize() {}

    /**
     * Takes the keyboard and cursor focus to the next logical text field when the user presses enter.
     * @param event - the source of which is the textField from which the enter key has been pressed
     */
    @FXML
    private void nextField(ActionEvent event) {
        if (event.getSource() == rotationAngle) {
            scaleFactorX.requestFocus();
        } else if (event.getSource() == scaleFactorX) {
            scaleFactorY.requestFocus();
        } else if (event.getSource() == scaleFactorY) {
            finalPositionX.requestFocus();
        } else if (event.getSource() == finalPositionX) {
            finalPositionY.requestFocus();
        }
    }

    /**
     * Sets the nodes in the reference node choice box according to the parsed file.
     * @param path - the path as set in the LoadController
     */
    void setNodes(Path path) {
        Parser parser = new Parser(path);
        referenceNodeChoiceBox.getItems().clear();
        nodes = parser.getNodes();
        Set<String> options = nodes.keySet();
        options.add("NULL");
        referenceNodeChoiceBox.getItems().addAll(options);
        referenceNodeChoiceBox.setMaxSize(1000,10);
    }

    /**
     * Checks if the input is valid
     * @return the validity of the input for ability to wrangle the data from its values
     */
    boolean inputIsValid() {
        try {
            Float.parseFloat(rotationAngle.getText());
            Float.parseFloat(scaleFactorX.getText());
            Float.parseFloat(scaleFactorY.getText());
            Float.parseFloat(finalPositionX.getText());
            Float.parseFloat(finalPositionY.getText());
            nodes.get(referenceNodeChoiceBox.getValue());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the output of the wrangled and formatted data changed with the appropriate inputs.
     * @param path - the path as set in the LoadController
     * @return a list of strings being the wrangled data having run through the correct file format creator
     */
    List<String> getOutput(Path path) {
        try {
            Node ref = nodes.get(referenceNodeChoiceBox.getValue());
            if(ref.equals("NULL")){
                ref = null;
           }
            Wrangler wrangler = new Wrangler(nodes);
            TreeMap<String,Node> nodeMap = wrangler.runTransformations(
                    Float.parseFloat(rotationAngle.getText()),
                    Float.parseFloat(scaleFactorX.getText()),
                    Float.parseFloat(scaleFactorY.getText()),
                    Float.parseFloat(finalPositionX.getText()),
                    Float.parseFloat(finalPositionY.getText()),
                    ref);

            Debugger debugger = new Debugger(nodeMap);
            nodeMap = new TreeMap<>(debugger.getMap());

            FileLinesCreator fileLinesCreator = new FileLinesCreator(nodeMap, path);
            return fileLinesCreator.getOutputFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
