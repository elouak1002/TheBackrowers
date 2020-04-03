package guitest;

import GUI.Root;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxRobot;
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

    public Path findPath(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        return file.toPath();
    }

    public void uploadFile(Path absolutePath){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(absolutePath.toString().replaceAll("%20"," "));
        clipboard.setContents(stringSelection, stringSelection);
        String osName = System.getProperty("os.name").toLowerCase();
            if(osName.indexOf("mac") >= 0){
                press(KeyCode.SHIFT).press(KeyCode.COMMAND).press(KeyCode.G).release(KeyCode.G).release(KeyCode.COMMAND).release(KeyCode.SHIFT);
                press(KeyCode.COMMAND).press(KeyCode.V).release(KeyCode.V).release(KeyCode.COMMAND);
            }
            else{
                press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
            }
            push(KeyCode.ENTER);

    }

    public void clickButton(String buttonName){
        Labeled toBeClicked = lookup(buttonName).query();
        clickOn(toBeClicked);
    }

    public void writeIn(String fieldName,String text){
        TextField typeIn=lookup(fieldName).query();
        clickOn(typeIn).write(text);
    }
}