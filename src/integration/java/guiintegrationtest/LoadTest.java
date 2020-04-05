package guiintegrationtest;

import GUI.LoadController;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoadController.class)
public class LoadTest extends AppRunner {

    @Test
    public void guiLoaded(){
		clickButton("#wranglerButton");

		setUpMock("fullInputData.txt");

        if (lookup("#uploadButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled uploadButton = lookup("#uploadButton").query();
            verifyThat(uploadButton, hasText("Upload File"));
        }
        if (lookup("#dragTarget").tryQuery().isPresent()) {//this waits for the text area to become visible
            TextArea dragTarget = lookup("#dragTarget").query();
            assertTrue(dragTarget.isVisible());
            assertEquals("Drop file here...", dragTarget.getPromptText());
        }
        if (lookup("#selectedFileLabel").tryQuery().isPresent()) {//this waits for the label to become visible
            Label selectedFileLabel = lookup("#selectedFileLabel").query();
            assertTrue(selectedFileLabel.isVisible());
            assertEquals("Selected File: ", selectedFileLabel.getText());
        }
        if (lookup("#next").tryQuery().isPresent()) {//this waits for the label to become visible
            Labeled nextButton = lookup("#next").query();
            assertTrue(nextButton.isVisible());
            assertEquals("Input", nextButton.getText());
        }

        clickButton("#previous");
    }
	
    @Test
    public void fileUploadTest(){
		clickButton("#wranglerButton");

		setUpMock("fullInputData.txt");

		if (lookup("#uploadButton").tryQuery().isPresent()) {//this waits for the button to become visible
            clickButton("#uploadButton");
            Label selectedFileLabel = lookup("#selectedFileLabel").query();
            assertEquals("Selected File: fullInputData.txt", selectedFileLabel.getText());
        }

        clickButton("#previous");
    }
}
