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

    private static Scene scene;
    private WebDriver driver;

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("🚀 JavaFX Application is Starting...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
            Parent root = loader.load();

            System.out.println("FXML Loaded Successfully");

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TickLinks UI Test");
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error Loading FXML: " + e.getMessage());
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

    // Declare WebDriver globally for mobile mode
    private void initializeMobileDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\chromedriver-win64\\chromedriver.exe");

            // Create Chrome Options
            ChromeOptions options = new ChromeOptions();

            // Enable Mobile Emulation Mode (iPhone X)
            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", 325);
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
            userId.sendKeys("dnag@gmail.com");
            WebElement passwordId = driver.findElement(By.xpath("//*[@id=\"nav-tabContent\"]/div/div[2]/input"));
            passwordId.sendKeys("uat");
            signInBtn = driver.findElement(By.xpath("//*[@id=\"nav-tabContent\"]/div/button"));
            signInBtn.click();

            Thread.sleep(4000); // Wait for possible error message
            if (isErrorDisplayed(driver)) {
                takeScreenshot(driver, "Invalid_Credentials_Error");
                logError("❌ Invalid Username or Password detected.");
                return;
            }

            // Wait for login confirmation
            if (waitForPageLoad(driver, 60) && waitForElement(driver, "//*[@id='divFeed']", 30)) {
                System.out.println("✅ Login Successful");
            } else {
                takeScreenshot(driver, "Login_Failed");
                logError("❌ Login failed or took too long.");
            }

            // Proceed to the GR module
            //run_GR();

        } catch (InterruptedException e) {
            takeScreenshot(driver, "Unexpected_Error");
            logError("❌ Unexpected Error: " + e.getMessage());
        }
    }

    // Method to handle GR module actions
    @FXML
    private void run_GR() {
        try {
            // Open menu before navigating to pages
            run_Login(null);

            takeScreenshot(driver, "Before_Click_GR");
            WebElement grItem = driver.findElement(By.xpath("//*[@id=\"divFeed\"]/div/div[3]/div/div[1]/div[1]"));
            grItem.click();
            System.out.println("✅ Clicked on GR item");

            Thread.sleep(4000);

            scrollAndClick(driver, "//*[@id=\"divFeed\"]/div/div[7]/div[18]/div[1]/a/small", "Chapter");

            System.out.println("✅ Clicked on Chapter");

            Thread.sleep(4000);
            WebElement period = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[34]/div[1]/div[2]/div[1]/div/div/div[2]/div/img"));
            period.click();
            System.out.println("✅ Clicked on Period");

            Thread.sleep(4000);
            WebElement PDFClick = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[34]/div[2]/div[1]/div/div[3]/div[1]/div[1]/div[2]/img"));
            PDFClick.click();
            System.out.println("✅ Clicked on Period");

            Thread.sleep(4000);
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(0));
            System.out.println("✅ Switched back to original tab");

            Thread.sleep(4000);
            WebElement backButton = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[34]/div[2]/div[1]/div/div[2]/div[1]/p/a/i"));
            backButton.click();
            System.out.println("✅ Clicked on Back Button");

            Thread.sleep(4000);

            WebElement backButton2 = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[34]/div[1]/div[1]/div/div[1]/div/i"));
            backButton2.click();
            System.out.println("✅ Clicked on Back Button 2");

            Thread.sleep(4000);
            scrollAndClick(driver, "//*[@id=\"divFeed\"]/div/div[1]/div[1]/div/button[2]/span", "closeButton");

            System.out.println("✅ Clicked on Cross Button to exit GR page");

            System.out.println("✅ Test completed successfully");

        } catch (InterruptedException e) {
            takeScreenshot(driver, "Unexpected_Error");
            logError("❌ Unexpected Error: " + e.getMessage());
        }
    }
    
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    
    @FXML
private void run_TLM() {
    try {
        run_Login(null);
        Thread.sleep(3000);

        takeScreenshot(driver, "Before_Click_TLM");

        scrollAndClick(driver, "/html/body/div[4]/div[2]/div[2]/div/div[1]/div/i", "TLM");
        System.out.println("✅ Clicked on TLM Page");

        Thread.sleep(5000);

        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[1]/div[2]/div/div/ul/li[1]/ul/li[12]/div/div[2]/span/i", "Subject");
        System.out.println("✅ Clicked on Subject");

        Thread.sleep(6000);
        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/a/small", "Chapter");
        System.out.println("✅ Clicked on Chapter");
        
        Thread.sleep(7000);
        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[2]/div[1]/div[2]/div/div[2]/div[3]/div[3]/div[1]/div[1]/div[2]/img", "Period");
        System.out.println("✅ Clicked on Peroid");
        
        Thread.sleep(6000);
        Actions actions = new Actions(driver);
        actions.moveByOffset(150,200).click().perform();
        System.out.println("clicked on media");
        
        Thread.sleep(20000);
        takeScreenshot(driver,"media acessed sucssesfully");
        
        scrollAndClick(driver,"/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[2]/div[8]/div[1]/div/div[2]/div[1]/p/a/i","back button 2");
        System.out.println("clicked on back button media page");
        
        
        Thread.sleep(2000);
        scrollAndClick(driver,"/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[1]/i\n","back button 2");
        System.out.println("clicked on back button on period page");
        
        Thread.sleep(2000);
        scrollAndClick(driver,"/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/i","back button 3");
        System.out.println("clicked on back button on Chapter page");
        
        Thread.sleep(2000);
        scrollAndClick(driver,"/html/body/div[4]/div[1]/div/div[1]/div/span/i","Home button");
        System.out.println("clicked on home button on subject page");
        
        Thread.sleep(4000);
        scrollAndClick(driver,"/html/body/div[4]/div[1]/div/div[2]/i[6]","Log Out button");
        System.out.println("clicked on Log out  button");
        
        
        System.out.println("✅ Test completed successfully");

    } catch (InterruptedException e) {
        takeScreenshot(driver, "Unexpected_Error");
        logError("❌ Unexpected Error: " + e.getMessage());
    } catch (Exception e) {
        takeScreenshot(driver, "General_Error");
        logError("❌ General Exception: " + e.getMessage());
    }
}



    
    @FXML 
    private void run_ClubActivities() throws AWTException {
    try {
        run_Login(null);

        if (!waitForElement(driver, "//*[@id='divFeed']/div/div[3]/div/div[1]/div[1]", 15)) {
            takeScreenshot(driver, "GR_Element_Not_Found");
            logError("❌ GR element not found after login.");
            return;
        }

        takeScreenshot(driver, "Before_Click_Club_Activities");

        WebElement AddTeacherStory = driver.findElement(By.xpath("//*[@id=\"divFeed\"]/div/div[1]/div[1]/button/span[2]"));
        AddTeacherStory.click();
        System.out.println("✅ Clicked on Add teacher story item");

        Thread.sleep(3000);

        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[1]/div[2]/select[1]", "language");
        System.out.println("✅ Clicked on language");

        Thread.sleep(2000);

        WebElement std = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[1]/div[2]/select[2]"));
        std.click();
        System.out.println("✅ Clicked on class item");

        Thread.sleep(2000);

        WebElement Katha2 = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[1]/div[3]/div/a/span[2]"));
        Katha2.click();
        System.out.println("✅ Clicked on Club name item");

        scrollAndClick(driver, "//*[@id=\"ui-select-choices-row-0-1\"]/div/div[2]", "Katha");
        System.out.println("✅ Clicked on club Katha");

        Thread.sleep(3000);
        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[2]/div/div/div[2]/div[3]/button/span[2]", "Upload");
        System.out.println("✅ Clicked on upload Button");
        
        Thread.sleep(3000);
        WebElement Mediaselect1 = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[2]/div[2]/div/ui-carousel/div/div[1]/div/div[2]/div/div[1]/a/img"));
        Mediaselect1.click();
        System.out.println("✅ Clicked on media select  item");
        
        Thread.sleep(3000);
        selectFile("\"C:\\Users\\Admin\\Downloads\\peter-vanosdall-uZVtAixV8c8-unsplash.jpg\"");
        
        Thread.sleep(4000);
        
        WebElement descriptionBox = driver.findElement(By.xpath("//textarea[@name='Description']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.pointerEvents = 'auto';", descriptionBox);
        descriptionBox.sendKeys("Image upload in club activities is successful.");
        takeScreenshot(driver, "Description_Field_Entered");
        System.out.println("✅ Description entered in textarea.");
        
        Thread.sleep(1000);
        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[4]/div[1]/div/div/form/div[7]/button", "save");
        Thread.sleep(3000);
        takeScreenshot(driver, "media is saved");
        System.out.println("✅ Clicked on save Button");
        
        Thread.sleep(10000);
        
        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[6]/div[1]/div/div[2]/div[1]/p/a/i", "Back Button");
        System.out.println("✅ Clicked on Back button Button");
        
        Thread.sleep(3000);
        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[2]/div/div/div[2]/div[3]/button/span[2]", "Upload");
        System.out.println("✅ Clicked on upload Button");
       
        
        WebElement Mediaselect2 = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[2]/div[2]/div/ui-carousel/div/div[1]/div/div[1]/div/div[2]/a/img"));
        Mediaselect2.click();
        System.out.println("✅ Clicked on pdf select item");
        
        Thread.sleep(3000);
        selectFile("\"C:\\Users\\Admin\\Downloads\\Refurbished laptop.pdf\"");
        
        Thread.sleep(4000);
        
        WebElement descriptionBox2 = driver.findElement(By.xpath("//textarea[@name='Description']"));

        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.pointerEvents = 'auto';", descriptionBox2);
        descriptionBox2.sendKeys("PDF upload in club activities is successful.");
        takeScreenshot(driver, "Description_Field_Entered");
        System.out.println("✅ Description entered in textarea.");
        

        Thread.sleep(1000);
        scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[4]/div[1]/div/div/form/div[7]/button", "save");
        Thread.sleep(3000);
        takeScreenshot(driver, "media is saved");
        System.out.println("✅ Clicked on save Button");
        
        System.out.println("✅ media upload test is completed");
       
    } catch (InterruptedException ex) {
        logError("❌ Exception in run_ClubActivities: " + ex.getMessage());
    }
}
    
    
public void selectFile(String path) throws AWTException {
    StringSelection strSelection = new StringSelection(path);
    
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(strSelection, null);

    Robot robot = new Robot();

    robot.delay(2000);
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.delay(4000);
    robot.keyRelease(KeyEvent.VK_ENTER);
}
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

    // Function to Scroll and Click
    private static void scrollAndClick(WebDriver driver, String xpath, String name) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(1000);
            element.click();
            System.out.println("✅ Clicked on " + name);
        } catch (InterruptedException e) {
            takeScreenshot(driver, name + "_ScrollClick_Failed");
            logError("❌ Failed to scroll and click " + name + ": " + e.getMessage());
        }
    }
    
    

    // Function to Check for Errors
    private static boolean isErrorDisplayed(WebDriver driver) {
        try {
            return !driver.findElements(By.xpath("//div[@class='toast-message']")).isEmpty();
        } catch (Exception e) {
            return false;
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

    // Function to Take Screenshot
    private static void takeScreenshot(WebDriver driver, String filename) {
        try {
            // Create main "Testing Issues" folder
            File mainFolder = new File("Testing Issues");
            if (!mainFolder.exists()) mainFolder.mkdir();

            // Create subfolder for the current date (YYYY-MM-DD)
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dailyFolder = new File(mainFolder, currentDate);
            if (!dailyFolder.exists()) dailyFolder.mkdir();

            // Generate timestamp for unique filenames
            String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(dailyFolder, filename + "_" + timestamp + ".png");

            // Save the screenshot
            FileHandler.copy(srcFile, screenshotFile);
            System.out.println("📸 Screenshot saved: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Screenshot failed: " + e.getMessage());
        }
    }

    // Function to Log Errors
    private static void logError(String message) {
        try {
            // Create main "Testing Issues" folder
            File mainFolder = new File("Testing Issues");
            if (!mainFolder.exists()) mainFolder.mkdir();

            // Create subfolder for the current date (YYYY-MM-DD)
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dailyFolder = new File(mainFolder, currentDate);
            if (!dailyFolder.exists()) dailyFolder.mkdir();

            // Log file path inside the daily folder
            File logFile = new File(dailyFolder, "log.txt");

            // Append error message with timestamp
            FileWriter writer = new FileWriter(logFile, true);
            writer.write(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " - " + message + "\n");
            writer.close();

            System.out.println("Logged error: " + message);
        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}

