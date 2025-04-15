package com.openlinksfoundation.org.selenium.automation;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

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
    
    //if button is clicked, then mobile mode, else no options
    WebDriver driver = new ChromeDriver(options);
    
    // Open the website in Mobile Mode
    try{
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
   

            // Wait for login confirmation
            if (waitForPageLoad(driver, 60) && waitForElement(driver, "//*[@id='divFeed']", 30)) {
                System.out.println("✅ Login Successful");
            } else {
                takeScreenshot(driver, "Login_Failed");
                logError("❌ Login failed or took too long.");
                return;
            }

            // Open menu before navigating to pages
            clickElement(driver, "Menu", "//*[@id='divFeed']/div/div[3]/i[2]");

            // Navigate and scroll on pages
            navigateAndScroll(driver, "GR", "//*[@id='rbnGR']", "//*[@id='GR_Unique_Section']", "//*[@id=\"divFeed\"]/div/div[1]/div[1]/div/button[2]/span");  
            navigateAndScroll(driver, "POM", "//*[@id='rbnPOM']", "//*[@id='POM_Unique_Section']", "//*[@id=\"divFeed\"]/div/div[1]/div[1]/div/button[2]/span");  
            navigateAndScroll(driver, "Forms", "//*[@id='rbnNeedResponse']", "//*[@id='Forms_Unique_Section']", "//*[@id=\"divFeed\"]/div/div[1]/div[1]/div/button[2]");  

            System.out.println("✅ Test completed successfully");

            // Keep browser open for 30 seconds after test
            Thread.sleep(30000);

        } catch (Exception e) {
            takeScreenshot(driver, "Unexpected_Error");
            logError("❌ Unexpected Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    // Function to Click on an Element
    private static void clickElement(WebDriver driver, String elementName, String xpath) {
        try {
            if (waitForElement(driver, xpath, 30)) {
                driver.findElement(By.xpath(xpath)).click();
                System.out.println("🔹 Clicked on " + elementName);
            } else {
                takeScreenshot(driver, elementName + "_Click_Failed");
                logError("❌ Could not find " + elementName + " to click.");
            }
        } catch (Exception e) {
            takeScreenshot(driver, elementName + "_Click_Error");
            logError("❌ Error clicking " + elementName + ": " + e.getMessage());
        }
    }

    // Function to Navigate to a Page, Scroll Down, and Check for Errors
    private static void navigateAndScroll(WebDriver driver, String pageName, String buttonXpath, String sectionXpath, String closeButtonXpath) {
    try {
        System.out.println("🔄 Navigating to " + pageName + "...");

        // Click on the page button
        WebElement pageButton = driver.findElement(By.xpath(buttonXpath));
        pageButton.click();
        System.out.println("🔹 Clicked on " + pageName);

        // Wait for the section to load
        if (waitForPageLoad(driver, 60) && waitForElement(driver, sectionXpath, 30)) {
            scrollDown(driver);
            System.out.println("✅ " + pageName + " loaded and scrolled down.");

            // 🔴 Add the correct XPath for the close button in the function call below
            if (waitForElement(driver, closeButtonXpath, 10)) {
                WebElement closeButton = driver.findElement(By.xpath(closeButtonXpath));

                closeButton.click();
                System.out.println("❎ Closed " + pageName + " section.");
            } else {
                takeScreenshot(driver, pageName + "_Close_Failed");
                logError("❌ Could not find close button for " + pageName);
            }
        } else {
            takeScreenshot(driver, pageName + "_Load_Failed");
            logError("❌ " + pageName + " did not load properly.");
        }
    } catch (Exception e) {
        takeScreenshot(driver, pageName + "_Error");
        logError("❌ Error on " + pageName + ": " + e.getMessage());
    }
}


    


    // Function to Wait for an Element
    private static boolean waitForElement(WebDriver driver, String xpath, int timeoutSec) {
        for (int i = 0; i < timeoutSec; i++) {
            try {
                Thread.sleep(1000);
                if (!driver.findElements(By.xpath(xpath)).isEmpty()) return true;
            } catch (InterruptedException ignored) {}
        }
        return false;
    }

    // Function to Wait for Page Load
    private static boolean waitForPageLoad(WebDriver driver, int maxSeconds) {
        for (int i = 0; i < maxSeconds; i++) {
            try {
                Thread.sleep(1000);
                if ("complete".equals(((JavascriptExecutor) driver).executeScript("return document.readyState"))) {
                    return true;
                }
            } catch (InterruptedException ignored) {}
        }
        return false;
    }

    // Function to Scroll Down
    private static void scrollDown(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
        System.out.println("📜 Scrolled down slightly");
    }

    // Function to Check for Errors
    private static boolean isErrorPresent(WebDriver driver) {
        return driver.getPageSource().contains("error") || driver.getPageSource().contains("Invalid credentials");
    }

    // Function to Take Screenshot
    private static void takeScreenshot(WebDriver driver, String filename) {
        try {
            // Create a folder with the current date
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File testDataDir = new File("TestData/" + date);
            if (!testDataDir.exists()) testDataDir.mkdirs();

            // Format filename with timestamp
            String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(testDataDir, filename + "_" + timestamp + ".png");

            FileHandler.copy(srcFile, destFile);
            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Screenshot failed: " + e.getMessage());
        }
    }


    // Function to Log Errors
    private static void logError(String message) {
        try {
            // Create folder for logs
            File testDataDir = new File("TestData");
            if (!testDataDir.exists()) testDataDir.mkdir();

            // Create or open log file
            FileWriter writer = new FileWriter("TestData/error_log.txt", true);

            // Append error with timestamp (HH:mm:ss)
            String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            writer.write(timestamp + " - " + message + "\n");
            writer.close();

            System.out.println("📝 Logged error: " + message);
        } catch (IOException e) {
            System.out.println("❌ Logging failed: " + e.getMessage());
        }
    } 

}