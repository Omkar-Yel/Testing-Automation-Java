package com.openlinksfoundation.org.selenium.automation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
    System.out.println("🚀 JavaFX Application is Starting...");

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        Parent root = loader.load();

        System.out.println("✅ FXML Loaded Successfully");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TickLinks UI Test");
        primaryStage.show();
    } catch (IOException e) {
        System.err.println("❌ Error Loading FXML: " + e.getMessage());
        e.printStackTrace();
    }
}


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    @FXML  // Connects the button from FXML
    private void run_Login(ActionEvent event) {
    System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\chromedriver-win64\\chromedriver.exe");

    // Create Chrome Options
    ChromeOptions options = new ChromeOptions();

    // Enable Mobile Emulation Mode (iPhone X)
    Map<String, Object> deviceMetrics = new HashMap<>();
    deviceMetrics.put("width", 375);
    deviceMetrics.put("height", 812);
    deviceMetrics.put("pixelRatio", 3.0);
    deviceMetrics.put("touch", true);

    Map<String, Object> mobileEmulation = new HashMap<>();
    mobileEmulation.put("deviceMetrics", deviceMetrics);
    mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Mobile Safari/537.36");

    options.setExperimentalOption("mobileEmulation", mobileEmulation);

    // Initialize WebDriver with Mobile Mode
    WebDriver driver = new ChromeDriver(options);
    
    // Open the website in Mobile Mode
    driver.get("https://www.fromkg.com");

    System.out.println("✅ Website opened in Mobile Mode");
}
    
    
}



