package guiintegrationtest;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import org.junit.Test;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import org.powermock.modules.junit4.PowerMockRunner;

import javafx.stage.FileChooser.ExtensionFilter;
import GUI.LoadController;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.api.mockito.PowerMockito;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Observable;
import javafx.beans.binding.ListBinding;
import javafx.collections.*;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoadController.class)
public class InputTest extends AppRunner {
    @Test
    public void testNodesSelection(){
        clickButton("#wranglerButton");

        setUpMock("fullInputData.txt");

        clickButton("#uploadButton");

        clickButton("#next");

        Label optionHintLabel = lookup("#optionHintLabel").query();
        assertTrue(optionHintLabel.getText().equals("To input final node positions instead, select a node"));

        ComboBox referenceNode = lookup("#referenceNode").query();
        clickOn(referenceNode);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        assertTrue(optionHintLabel.getText().equals("To input shift factors instead, select 'NO REFERENCE'"));

        clickButton("#previous");
        clickButton("#previous");
    }
}
