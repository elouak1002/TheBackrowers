package guiintegrationtest;

import GUI.Root;
import javafx.collections.ObservableList;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import org.junit.After;
import org.junit.Before;
import org.powermock.api.mockito.PowerMockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Major credit to https://github.com/bazylMN/testFX-junit5
 */
public class AppRunner extends ApplicationTest {

    @Before
    public void runAppToTests() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Root::new);
        FxToolkit.showStage();
    }

    @After
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    //Helper Methods For tests

    public Path findPath(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile().replaceAll("%20"," "));
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
        } catch (Exception ignored) {
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