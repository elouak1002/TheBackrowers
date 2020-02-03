package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controller {
    @FXML private Slider rotationAngleSlider;
    @FXML private TextField rotationAngleField;

    @FXML
    private void sliderToField() {
        rotationAngleField.setText(String.valueOf(rotationAngleSlider.getValue()));
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
}
