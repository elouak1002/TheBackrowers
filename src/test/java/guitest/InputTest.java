package guitest;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputTest extends AppRunner{
    @Test
    public void testNodesSelection(){

        clickButton("#wranglerButton");
        clickButton("#uploadButton");

        uploadTestInput();

        clickButton("#next");

        TextField rotationAngle=lookup("#rotationAngle").query();
        clickOn(rotationAngle).write("10");


        Label optionHintLabel = lookup("#optionHintLabel").query();
        assertTrue(optionHintLabel.getText().equals("To input final node positions instead, select a node"));

        ComboBox referenceNode = lookup("#referenceNode").query();
        clickOn(referenceNode);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        TextField scaleFactorX=lookup("#scaleFactorX").query();
        clickOn(scaleFactorX).write("12");

        TextField scaleFactorY=lookup("#scaleFactorY").query();
        clickOn(scaleFactorY).write("14");

        TextField positionOrShiftX=lookup("#positionOrShiftX").query();
        clickOn(positionOrShiftX).write("13");

        TextField positionOrShiftY=lookup("#positionOrShiftY").query();
        clickOn(positionOrShiftY).write("15");

        assertTrue(optionHintLabel.getText().equals("To input shift factors instead, select 'NO REFERENCE'"));

        //clickOn(nextButton);
    }
}
