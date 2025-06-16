package com.openlinksfoundation.org.selenium.automation;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

public class App extends Application {

    private static Scene Scene;
    private WebDriver driver;

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("🚀 JavaFX Application is Starting...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
            Parent root = loader.load();

            System.out.println("FXML Loaded Successfully");

            Scene = new Scene(root);
            primaryStage.setScene(Scene);
            primaryStage.setTitle("TickLinks UI Test");
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error Loading FXML: " + e.getMessage());
        }
    }

    static void setRoot(String fxml) throws IOException {
        Scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        try {
            HelperFunctions.selectFile("C:\\Users\\User\\Documents\\file.pdf");
        } catch (AWTException e) {
        }
    }

    // Declare WebDriver globally for mobile mode
    private void initializeMobileDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\chromedriver-win64\\chromedriver.exe");

            // Create Chrome Options
            ChromeOptions options = new ChromeOptions();

            // Enable Mobile Emulation Mode (iPhone X)
            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", 320);
            deviceMetrics.put("height", 528);
            deviceMetrics.put("pixelRatio", 2.0);
            deviceMetrics.put("touch", true);

            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Mobile Safari/537.36");

            options.setExperimentalOption("mobileEmulation", mobileEmulation);
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            
          }
    }

    // Method to handle login
    @FXML // Connects the button from FXML
    private void run_Login(ActionEvent event) {
        initializeMobileDriver();

        try {
            driver.get("https://www.fromkg.com");

            System.out.println("✅ Website opened in Mobile Mode");
            WebElement signInBtn = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div/div/div[1]/ul/li[2]/span"));
    signInBtn.click();
    signInBtn = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div/div/div[2]/div/div[1]/div/div/button[1]"));
    signInBtn.click();
    WebElement userId = driver.findElement(By.xpath("//*[@id=\"nav-tabContent\"]/div/div[1]/input"));
    userId.sendKeys("8530897612");
    WebElement passwordId = driver.findElement(By.xpath("//*[@id=\"nav-tabContent\"]/div/div[2]/input"));
    passwordId.sendKeys("uat");
    signInBtn = driver.findElement(By.xpath("//*[@id=\"nav-tabContent\"]/div/button"));
    signInBtn.click();
            
            

            Thread.sleep(4000); // Wait for possible error message
            if (HelperFunctions.isErrorDisplayed(driver)) {
                HelperFunctions.takeScreenshot(driver, "Invalid_Credentials_Error");
                HelperFunctions.logError("❌ Invalid Username or Password detected.");
                return;
            }

            // Wait for login confirmation
            if (HelperFunctions.waitForPageLoad(driver, 60) && HelperFunctions.waitForElement(driver, "//*[@id='divFeed']", 30)) {
                System.out.println("✅ Login Successful");
            } else {
                HelperFunctions.takeScreenshot(driver, "Login_Failed");
                HelperFunctions.logError("❌ Login failed or took too long.");
            }

            // Proceed to the GR module
            //run_GR();

        } catch (InterruptedException e) {
            HelperFunctions.takeScreenshot(driver, "Unexpected_Error");
            HelperFunctions.logError("❌ Unexpected Error: " + e.getMessage());
        }
    }

    // Method to handle GR module actions
    @FXML
private void run_GR(ActionEvent event) {
    initializeMobileDriver();
    run_Login(null);

    GRPage grPage = new GRPage(driver);
    grPage.runGR();
}
    
    @FXML
private void run_TLM(ActionEvent event) {
    initializeMobileDriver();
    run_Login(null); // optional if login needed before TLM

    TLMPage tlmPage = new TLMPage(driver);
    tlmPage.runTLM();
}

// Method to handle Stories module actions
    @FXML
private void run_Stories() throws AWTException {
    initializeMobileDriver();
    run_Login(null); // ensure login is run first

    StoriesPage storiesPage = new StoriesPage(driver);
    storiesPage.runStories();
}

    
    @FXML
private void run_ClubActivities() throws AWTException {
    initializeMobileDriver();  // your existing WebDriver setup
    run_Login(null);           // run login flow

    ClubActivitiesPage clubPage = new ClubActivitiesPage(driver);
    clubPage.runClubActivities();
}

@FXML
private void run_SanityTesting() throws AWTException, InterruptedException {
    run_Login(null); // Assumes login method is modular
    SanityTestingPage sanity = new SanityTestingPage(driver);
    sanity.runSanityTest();
}
}