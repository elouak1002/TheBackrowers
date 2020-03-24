package guitest;
import GUI.Root;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XMLGeneratorTest extends AppRunner {

    @Test
    public void guiLoaded(){
        clickButton("#xmlButton");

        Label saveNotification = lookup("#saveNotification").query();
        assertTrue(!saveNotification.isVisible());
        assertTrue(saveNotification.getText().equals("File has been saved!"));
    }

    @Test
    public void testFiles(){
        clickButton("#xmlButton");

        clickButton("#uploadButton");
        uploadFile(findPath("XMLParserTestData1.txt"));
        clickButton("#uploadButton");
        uploadFile(findPath("XMLParserTestData2.txt"));

    }

}