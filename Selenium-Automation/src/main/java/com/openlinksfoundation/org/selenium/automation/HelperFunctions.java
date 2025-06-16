/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.openlinksfoundation.org.selenium.automation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

public class HelperFunctions {

    /**
     * Uses Robot class to paste a file path in an OS file chooser dialog.
     * 
     * @param path The absolute path of the file to select.
     * @throws AWTException If Robot class cannot be instantiated.
     */
    public static void selectFile(String path) throws AWTException {
        StringSelection strSelection = new StringSelection(path);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.getSystemClipboard().setContents(strSelection, null);

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

    /**
     * Scrolls the element into view using JavaScript and clicks it.
     * 
     * @param driver WebDriver instance
     * @param xpath XPath locator of the element
     * @param name Friendly name of the element for logging
     */
    public static void scrollAndClick(WebDriver driver, String xpath, String name) {
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

    /**
     * Checks if an error toast message is displayed on the page.
     * 
     * @param driver WebDriver instance
     * @return true if error message present; false otherwise
     */
    public static boolean isErrorDisplayed(WebDriver driver) {
        try {
            return !driver.findElements(By.xpath("//div[@class='toast-message']")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for an element to appear on the page for the specified timeout.
     * 
     * @param driver WebDriver instance
     * @param xpath XPath locator of the element to wait for
     * @param timeoutSec Timeout in seconds
     * @return true if element appears; false if timeout expires
     */
    public static boolean waitForElement(WebDriver driver, String xpath, int timeoutSec) {
        for (int i = 0; i < timeoutSec; i++) {
            try {
                Thread.sleep(1000);
                if (!driver.findElements(By.xpath(xpath)).isEmpty()) return true;
            } catch (InterruptedException ignored) {}
        }
        return false;
    }

    /**
     * Waits for the page to fully load (document.readyState == "complete").
     * 
     * @param driver WebDriver instance
     * @param maxSeconds Maximum wait time in seconds
     * @return true if page loaded within timeout; false otherwise
     */
    public static boolean waitForPageLoad(WebDriver driver, int maxSeconds) {
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

    /**
     * Takes a screenshot and saves it in a folder structure: Testing Issues/YYYY-MM-DD/
     * 
     * @param driver WebDriver instance
     * @param filename Name to save the screenshot as (without extension)
     */
    public static void takeScreenshot(WebDriver driver, String filename) {
        try {
            File mainFolder = new File("Testing Issues");
            if (!mainFolder.exists()) mainFolder.mkdir();

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dailyFolder = new File(mainFolder, currentDate);
            if (!dailyFolder.exists()) dailyFolder.mkdir();

            String timestamp = new SimpleDateFormat("HHmmss").format(new Date());

            File screenshotFile = new File(dailyFolder, filename + "_" + timestamp + ".png");

            FileHandler.copy(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), screenshotFile);

            System.out.println("📸 Screenshot saved: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Screenshot failed: " + e.getMessage());
        }
    }

    /**
     * Logs an error message with timestamp into Testing Issues/YYYY-MM-DD/log.txt.
     * 
     * @param message Error message to log
     */
    public static void logError(String message) {
        try {
            File mainFolder = new File("Testing Issues");
            if (!mainFolder.exists()) mainFolder.mkdir();

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dailyFolder = new File(mainFolder, currentDate);
            if (!dailyFolder.exists()) dailyFolder.mkdir();

            File logFile = new File(dailyFolder, "log.txt");

            FileWriter writer = new FileWriter(logFile, true);
            writer.write(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " - " + message + "\n");
            writer.close();

            System.out.println("Logged error: " + message);
        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}
