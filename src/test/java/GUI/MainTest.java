
package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.*;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class MainTest extends ApplicationTest {


    @Start
    public void start(Stage stage) {
        final Root app = new Root();

        final Scene scene = new Scene(app.getCurrentPage(), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

/*    @Test
    void startApplication() throws InterruptedException {
        wait(1000);
        FxRobotInterface fxRobotInterface;
        clickOn("#wranglerButton");
        wait(1000);
    }*/

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


}
