package GUI;

import ALG.*;
import filecreator.FileCreator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.TreeMap;

public class InputController {
    @FXML private Slider rotationAngleSlider;
    @FXML private TextField rotationAngleField;
    @FXML private ChoiceBox<String> referenceNodeChoiceBox;
    @FXML private TextField scaleFactorX;
    @FXML private TextField scaleFactorY;
    @FXML private TextField finalPositionX;
    @FXML private TextField finalPositionY;
    private Wrangler wrangler;
    private TreeMap<String,Node> nodes;
    private Path path;

    @FXML
    public void initialize() {}

    @FXML
    private void sliderToField() {
        rotationAngleField.setText(String.valueOf(
                new DecimalFormat("#").format(rotationAngleSlider.getValue())));
    }

    @FXML
    private void fieldToSlider() {
        boolean isNumber;
        double value = 0;
        try {
            value = Double.parseDouble(rotationAngleField.getText());
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        if (isNumber) rotationAngleSlider.setValue(value);
    }

    void setNodes(Path path) {
        this.path = path;
        Parser parser = new Parser(path);
        try {
            referenceNodeChoiceBox.getItems().clear();
            nodes = parser.createNodes(parser.getLines());
            referenceNodeChoiceBox.getItems().addAll(nodes.keySet());
            wrangler = new Wrangler(nodes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean inputIsValid() {
        try {
            Float.parseFloat(rotationAngleField.getText());
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

    void inputToOutput(OutputController outputController) {
        try {
            FileCreator fileCreator = new FileCreator(wrangler.runTransformations(
                    Float.parseFloat(rotationAngleField.getText()),
                    Float.parseFloat(scaleFactorX.getText()),
                    Float.parseFloat(scaleFactorY.getText()),
                    Float.parseFloat(finalPositionX.getText()),
                    Float.parseFloat(finalPositionY.getText()),
                    nodes.get(referenceNodeChoiceBox.getValue())
            ), path, path);
            //outputController.setOutputText(fileCreator.processOutputFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showNodes() {
        referenceNodeChoiceBox.show();
    }
}
