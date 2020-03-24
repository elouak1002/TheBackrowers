package guitest;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutputTest extends AppRunner {

    @Test
    public void testNodesSelection(){
        clickButton("#wranglerButton");
        clickButton("#uploadButton");
        uploadFile(findPath("fullInputData.txt"));
        clickButton("#next");
        fillInput();
        clickButton("#next");
        TextArea outputText = lookup("#outputText").query();
        String output = outputText.getText();


        assertTrue(output.contains("Node HenRaph_04_493_264 = new Node( 98.04f , -81.3f , GuysHeights.HenRaph_04 )"));
        assertTrue(output.endsWith("HenRaph_04_579_358\n"));
    }

    public void fillInput(){
        ComboBox referenceNode = lookup("#referenceNode").query();
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
