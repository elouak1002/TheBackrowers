package guiintegrationtest;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import org.testfx.api.FxRobot;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Path;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import GUI.LoadController;


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
            assertTrue(dragTarget.getPromptText().equals("Drop file here..."));
        }
        if (lookup("#selectedFileLabel").tryQuery().isPresent()) {//this waits for the label to become visible
            Label selectedFileLabel = lookup("#selectedFileLabel").query();
            assertTrue(selectedFileLabel.isVisible());
            assertTrue(selectedFileLabel.getText().equals("Selected File: "));
        }
        if (lookup("#next").tryQuery().isPresent()) {//this waits for the label to become visible
            Labeled nextButton = lookup("#next").query();
            assertTrue(nextButton.isVisible());
            assertTrue(nextButton.getText().equals("Input"));
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
            assertTrue(selectedFileLabel.getText().equals("Selected File: fullInputData.txt"));

        }
        clickButton("#previous");
    }
}
