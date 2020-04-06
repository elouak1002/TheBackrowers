package guiintegrationtest;

import GUI.LoadController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoadController.class)
public class OutputTest extends AppRunner {

    @Test
    public void testNodesSelection(){
		clickButton("#wranglerButton");
		setUpMock("fullInputData.txt");
        clickButton("#uploadButton");
        clickButton("#next");
        fillInput();
        clickButton("#next");
        TextArea outputText = lookup("#outputText").query();
        String output = outputText.getText();

        assertTrue(output.contains("Node HenRaph_04_493_264 = new Node( 98.04f , -81.3f , GuysHeights.HenRaph_04 )"));
        assertTrue(output.endsWith("HenRaph_04_579_358\n"));

        clickButton("#previous");
        press(KeyCode.ENTER);
        clickButton("#previous");
        clickButton("#previous");
    }

    public void fillInput(){
        ComboBox<String> referenceNode = lookup("#referenceNode").query();
        clickOn(referenceNode);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        writeIn("#rotationAngle","12");
        writeIn("#scaleFactorX","12");
        writeIn("#scaleFactorY","14");
        writeIn("#positionOrShiftX","13");
        writeIn("#positionOrShiftY","15");
    }
}