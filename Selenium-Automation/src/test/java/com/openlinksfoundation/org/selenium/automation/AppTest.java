package com.openlinksfoundation.org.selenium.automation;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {

    /**
     * Initializes JavaFX environment (needed for headless testing)
     */
    static {
        new JFXPanel(); // Initializes JavaFX environment
    }

    /**
     * Test of start method, of class App.
     */
    @Test
    public void testStart() {
        System.out.println("Testing App.start()");
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                try {
                    new App().start(new Stage()); // ✅ Real Stage object
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception in start(): " + e.getMessage());
                }
            });
        });
    }

    /**
     * Test of setRoot method, of class App.
     */
    @Test
    public void testSetRoot() {
        System.out.println("Testing App.setRoot()");
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                try {
                    App.setRoot("MainScreen"); // Use resource name, not full path
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Exception in setRoot(): " + e.getMessage());
                }
            });
        });
    }

    /**
     * Test of main method, of class App.
     */
    @Test
    public void testMain() {
        System.out.println("Testing App.main()");
        assertDoesNotThrow(() -> {
            String[] args = {};
            App.main(args);
        });
    }
}
