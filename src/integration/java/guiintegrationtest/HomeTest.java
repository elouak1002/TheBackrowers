package guiintegrationtest;

import javafx.scene.control.Labeled;

import org.junit.Test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class HomeTest extends AppRunner {

    @Test
    public void wranglerButtonWorks() {
		clickButton("#wranglerButton");
        if (lookup("#uploadButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled uploadButton = lookup("#uploadButton").query();
            verifyThat(uploadButton, hasText("Upload File"));
        }
    }

    @Test
    public void xmlButtonWorks() {
        clickButton("#xmlButton");
        if (lookup("#saveButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled saveButton = lookup("#saveButton").query();
            verifyThat(saveButton, hasText("Merge and Save"));
        }
    }

    @Test
    public void logButtonWorks() {
        clickButton("#log");
        if (lookup("#saveButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled saveButton = lookup("#saveButton").query();
            verifyThat(saveButton, hasText("Save"));
        }
    }
}
