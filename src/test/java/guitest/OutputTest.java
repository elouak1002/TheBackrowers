package guitest;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutputTest extends AppRunner {

    @Test
    public void testNodesSelection(){
        clickButton("#wranglerButton");
        clickButton("#uploadButton");
        uploadTestInput();
        clickButton("#next");
        fillInput();
        clickButton("#next");
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
