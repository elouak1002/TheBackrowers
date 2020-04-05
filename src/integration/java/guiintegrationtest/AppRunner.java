package guiintegrationtest;

import GUI.LoadController;
import GUI.Root;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.After;
import org.testfx.api.FxToolkit;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;

import org.testfx.util.WaitForAsyncUtils;

import static org.mockito.Mockito.spy;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.Test;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javafx.stage.FileChooser.ExtensionFilter;
import GUI.XMLGeneratorController;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import static org.mockito.Mockito.*;

import java.beans.Transient;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import javafx.beans.binding.ListBinding;
import org.junit.Before;
import javafx.collections.*;
import org.mockito.Mock;
import java.util.List;
import org.junit.Ignore;
import guiintegrationtest.*;

import static org.junit.Assert.*;


/**
 * Major credit to https://github.com/bazylMN/testFX-junit5
 */
public class AppRunner extends ApplicationTest {
    Root root;
    Scene scene;
    Stage stage;

    @Before
    public void runAppToTests() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Root::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @After
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    public void start(Stage stage) {
        root = new Root();
        root.start(stage);
        scene = root.getScene();
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    //Helper Methods For tests

    public Path findPath(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile().replaceAll("%20"," "));
        return file.toPath();
    }

	public void setUpMock(String fileName) {
		FileChooser mockFileChooser = PowerMockito.mock(FileChooser.class);
		ObservableList<ExtensionFilter> mockList = PowerMockito.mock(ObservableList.class);
		List<File> files = new ArrayList<>();
		files.add(findPath(fileName).toFile());
        when(mockFileChooser.showOpenMultipleDialog(null)).thenReturn(files);
		when(mockFileChooser.showOpenDialog(any(Window.class))).thenReturn(findPath(fileName).toFile());
        when(mockFileChooser.getExtensionFilters()).thenReturn(mockList);
        when(mockList.addAll()).thenReturn(true);
		
        try {
			PowerMockito.whenNew(FileChooser.class).withNoArguments().thenReturn(mockFileChooser);
        } catch (Exception e) {
		}
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