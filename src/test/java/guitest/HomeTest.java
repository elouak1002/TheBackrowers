package guitest;

import javafx.scene.control.Labeled;
import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class HomeTest extends AppRunner {

    @Test
    public void guiLoaded(){
        Labeled wranglerButton = lookup("#wranglerButton").query();
        verifyThat(wranglerButton, hasText("Data Wrangler"));

        Labeled xmlButton = lookup("#xmlButton").query();
        verifyThat(xmlButton, hasText("XML Generator"));

        Labeled logButton = lookup("#log").query();
        verifyThat(logButton, hasText("Log"));
    }

    @Test
    public void wranglerButtonWorks() {
        Labeled wranglerButton = lookup("#wranglerButton").query();
        clickOn(wranglerButton);
        if (lookup("#uploadButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled uploadButton = lookup("#uploadButton").query();
            verifyThat(uploadButton, hasText("Upload File"));
        }
    }

    @Test
    public void xmlButtonWorks() {
        Labeled xmlButton = lookup("#xmlButton").query();
        clickOn(xmlButton);
        if (lookup("#saveButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled saveButton = lookup("#saveButton").query();
            verifyThat(saveButton, hasText("Merge and Save"));
        }
    }

    @Test
    public void logButtonWorks() {
        Labeled logButton = lookup("#log").query();
        clickOn(logButton);
        if (lookup("#saveButton").tryQuery().isPresent()) {//this waits for the button to become visible
            Labeled saveButton = lookup("#saveButton").query();
            verifyThat(saveButton, hasText("Save"));
        }
    }
}
