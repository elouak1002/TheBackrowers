package GUI;

import ALG.Parser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;


public class InputController {
    @FXML private Slider rotationAngleSlider;
    @FXML private TextField rotationAngleField;
    @FXML private ChoiceBox<String> referenceNodeChoiceBox;
    @FXML private TextField scaleFactorX;
    @FXML private TextField scaleFactorY;
    @FXML private TextField finalPositionX;
    @FXML private TextField finalPositionY;

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

    //stand-in method for file chooser to replace
    @FXML
    private void setNodes() {
        Parser parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        try {
            referenceNodeChoiceBox.getItems().clear();
            referenceNodeChoiceBox.getItems().addAll(parser.createNodes(parser.getLines()).keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showNodes() {
        referenceNodeChoiceBox.show();
    }

}
