package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest extends ApplicationTest {


    @Override
    public void start(Stage stage) throws Exception{
        Parent mainNode = FXMLLoader.load(Main.class.getResource("Home.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    void startApplication() throws InterruptedException {
        wait(1000);
        FxRobotInterface fxRobotInterface = clickOn("#wranglerButton");
        wait(1000);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


}