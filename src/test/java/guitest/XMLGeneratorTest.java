package guitest;
import GUI.Root;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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

        ListView uploadTable = lookup("#uploadTable").query();

        uploadTable.getFocusModel().focus(0);
        uploadTable.getSelectionModel().select(0);
        clickButton("#selectedFiles");

        uploadTable.getFocusModel().focus(0);
        uploadTable.getSelectionModel().select(0);
        clickButton("#selectedFiles");

        clickButton("#next");
        clickOn("Close");

        TextArea outputText = lookup("#outputText").query();
        String output = outputText.getText();

        assertTrue(output.contains(" <room id=\"3\" x=\"47.61459\" y=\"26.463207\" Floor=\"4\" name=\"HR 4.2\">\n" +
                "  <neighbour id=\"8\"/>\n" +
                " </room>"));
    }

}