package guiintegrationtest;

import GUI.LoadController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoadController.class)
public class InputTest extends AppRunner {

    @Test
    public void testNodesSelection(){
        clickButton("#wranglerButton");

        setUpMock("fullInputData.txt");

        clickButton("#uploadButton");

        clickButton("#next");

        Label optionHintLabel = lookup("#optionHintLabel").query();
        assertEquals("To input final node positions instead, select a node", optionHintLabel.getText());

        ComboBox<String> referenceNode = lookup("#referenceNode").query();
        clickOn(referenceNode);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        assertEquals("To input shift factors instead, select 'NO REFERENCE'", optionHintLabel.getText());

        clickButton("#previous");
        clickButton("#previous");
    }
}
