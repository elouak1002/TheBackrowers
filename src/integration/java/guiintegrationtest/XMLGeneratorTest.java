package guiintegrationtest;

import GUI.Root;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;
import javafx.scene.input.KeyCode;

import org.junit.Test;
import static org.junit.Assert.*;

import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import GUI.XMLGeneratorController;


@RunWith(PowerMockRunner.class)
@PrepareForTest(XMLGeneratorController.class)
public class XMLGeneratorTest extends AppRunner {

    @Test
    public void guiLoaded(){
        clickButton("#xmlButton");

        Label saveNotification = lookup("#saveNotification").query();
        assertTrue(!saveNotification.isVisible());
        assertTrue(saveNotification.getText().equals("File has been saved!"));

        clickButton("#previous");
    }

    @Test
    public void testFiles(){
		clickButton("#xmlButton");
		
		setUpMock("XMLParserTestData1.txt");

		clickButton("#uploadButton");
		
		setUpMock("XMLParserTestData2.txt");

        clickButton("#uploadButton");

        ListView uploadTable = lookup("#uploadTable").query();

        uploadTable.getFocusModel().focus(0);
        uploadTable.getSelectionModel().select(0);
        clickButton("#selectedFiles");

        uploadTable.getFocusModel().focus(0);
        uploadTable.getSelectionModel().select(0);
        clickButton("#selectedFiles");

        clickButton("#next");
        press(KeyCode.ESCAPE);

        TextArea outputText = lookup("#outputText").query();
        String output = outputText.getText();

        assertTrue(output.contains(" <room id=\"3\" x=\"47.61459\" y=\"26.463207\" Floor=\"4\" name=\"HR 4.2\">\n" +
                "  <neighbour id=\"8\"/>\n" +
                " </room>"));

        clickButton("#previous");
        press(KeyCode.ENTER);
    }
    @Test
    public void testClearFiles(){
		clickButton("#xmlButton");

		setUpMock("XMLParserTestData1.txt");

        clickButton("#uploadButton");
        ListView uploadTable = lookup("#uploadTable").query();
        uploadTable.getFocusModel().focus(0);
        uploadTable.getSelectionModel().select(0);
        clickButton("#selectedFiles");

       assertTrue(uploadTable.getSelectionModel().isEmpty());

        ListView selectedTable = lookup("#selectedTable").query();
        clickButton("#clearAllButton");
        press(KeyCode.ENTER);
        assertTrue(selectedTable.getSelectionModel().isEmpty());

        clickButton("#previous");
     }

     @Test
    public void testLogUpdate(){
		 clickButton("#xmlButton");
		 setUpMock("XMLParserTestData1.txt");
         clickButton("#uploadButton");
         ListView uploadTable = lookup("#uploadTable").query();
         uploadTable.getFocusModel().focus(0);
         uploadTable.getSelectionModel().select(0);
         clickButton("#selectedFiles");
         clickButton("#next");

         clickOn("View Log");
         TextArea logArea = lookup("#displayLog").query();
         String logText = logArea.getText();

        assertTrue(logText.contains("HenRaph_04_491_365 has no neighbour, so it was removed.\n" +
                "HenRaph_04_696_341 has no neighbour, so it was removed.\n" +
                "\n"));

         clickButton("#previous");
         clickButton("#previous");
         press(KeyCode.ENTER);
     }
}