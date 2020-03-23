package guitest;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
public class LoadTest extends AppRunner{

    @Test
    public void guiLoaded(){
        Labeled wranglerButton = lookup("#wranglerButton").query();
        verifyThat(wranglerButton, hasText("Data Wrangler"));
        clickOn(wranglerButton);
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

    }

    public void uploadFile(){
        String resourceName = "fullInputData.txt";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        Path absolutePath = file.toPath();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(absolutePath.toString().replaceAll("%20"," "));
        clipboard.setContents(stringSelection, stringSelection);
        press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
        push(KeyCode.ENTER);

    }
    @Test
    public void fileUploadTest(){
        Labeled wranglerButton = lookup("#wranglerButton").query();
        clickOn(wranglerButton);
        if (lookup("#uploadButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled uploadButton = lookup("#uploadButton").query();
            clickOn(uploadButton);
            uploadFile();
            Label selectedFileLabel = lookup("#selectedFileLabel").query();
            assertTrue(selectedFileLabel.getText().equals("Selected File: fullInputData.txt"));
        }
    }
}
