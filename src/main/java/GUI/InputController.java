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
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * The InputController class allows the user to input the various numbers needed to wrangle the data,
 * along with choosing the reference node passed from the parsed input file from a drop down menu.
 */
public class InputController {
    @FXML private ChoiceBox<String> referenceNodeChoiceBox;
    @FXML private TextField rotationAngle;
    @FXML private TextField scaleFactorX;
    @FXML private TextField scaleFactorY;
    @FXML private TextField positionOrShiftX;
    @FXML private TextField positionOrShiftY;
    @FXML private Label positionOrShiftLabel;
    @FXML private Label optionHintLabel;
    private TreeMap<String,Node> nodes;

    @FXML
    public void initialize() {
        referenceNodeChoiceBox.setOnAction(event -> {
            if (!referenceNodeChoiceBox.getValue().equals("NO REFERENCE")) {
                optionHintLabel.setText("To input shift factors instead, select 'NO REFERENCE'");
                positionOrShiftLabel.setText("Final Node Positions");
            } else {
                optionHintLabel.setText("To input final node positions instead, select a node");
                positionOrShiftLabel.setText("Shift Factor");
            }
        });
    }

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
            positionOrShiftX.requestFocus();
        } else if (event.getSource() == positionOrShiftX) {
            positionOrShiftY.requestFocus();
        }
    }

    /**
     * Sets the nodes in the reference node choice box according to the parsed file.
     * @param path - the path as set in the LoadController
     */
    void setNodes(Path path) {
        Parser parser = new Parser(path);
        referenceNodeChoiceBox.getItems().clear();
        try {
            nodes = parser.createNodes();
        } catch (IOException e) {
            e.printStackTrace();
        }
      
        referenceNodeChoiceBox.getItems().add("NO REFERENCE");
        referenceNodeChoiceBox.getItems().addAll(nodes.keySet().stream().filter(nodeName -> nodes.get(nodeName).getStatus() == Status.INITIALISED).collect(Collectors.toList()));
        referenceNodeChoiceBox.getSelectionModel().selectFirst();
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
            Float.parseFloat(positionOrShiftX.getText());
            Float.parseFloat(positionOrShiftY.getText());
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
            Node ref;
            if (referenceNodeChoiceBox.getValue().equals("NO REFERENCE")) {
                ref = null;
            } else {
                ref = nodes.get(referenceNodeChoiceBox.getValue());
            }
            Wrangler wrangler = new Wrangler(nodes);
            TreeMap<String,Node> nodeMap = wrangler.runTransformations(
                    Float.parseFloat(rotationAngle.getText()),
                    Float.parseFloat(scaleFactorX.getText()),
                    Float.parseFloat(scaleFactorY.getText()),
                    Float.parseFloat(positionOrShiftX.getText()),
                    Float.parseFloat(positionOrShiftY.getText()),
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
