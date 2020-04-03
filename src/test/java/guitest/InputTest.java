package guitest;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputTest extends AppRunner {
    @Test
    public void testNodesSelection(){
        clickButton("#wranglerButton");
        clickButton("#uploadButton");
        uploadFile(findPath("fullInputData.txt"));

        clickButton("#next");


        Label optionHintLabel = lookup("#optionHintLabel").query();
        assertTrue(optionHintLabel.getText().equals("To input final node positions instead, select a node"));

        ComboBox referenceNode = lookup("#referenceNode").query();
        clickOn(referenceNode);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        assertTrue(optionHintLabel.getText().equals("To input shift factors instead, select 'NO REFERENCE'"));

        clickButton("#previous");
        clickButton("#previous");
    }
}
