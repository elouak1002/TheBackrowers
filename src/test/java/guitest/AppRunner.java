package guitest;

import GUI.Root;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

/**
 * Major credit to https://github.com/bazylMN/testFX-junit5
 */
class AppRunner extends ApplicationTest {
    @BeforeEach
    public void runAppToTests() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Root::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.toFront();
    }

    //Helper Methods For tests
    public void uploadTestInput(){
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
}
