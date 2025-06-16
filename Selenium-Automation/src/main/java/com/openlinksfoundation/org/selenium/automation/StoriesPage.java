/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.openlinksfoundation.org.selenium.automation;

import java.awt.AWTException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class StoriesPage {

    private WebDriver driver;

    public StoriesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void runStories() throws AWTException {
        try {
            HelperFunctions.takeScreenshot(driver, "Before_Click_Stories_page");

            Thread.sleep(5000);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div[2]/i[4]")).click();
            System.out.println("✅ Clicked on stories page");

            Thread.sleep(6000);
            HelperFunctions.scrollAndClick(driver, "//*[@id=\"divList\"]/div/div[2]/div[2]/ul/li[7]/div[3]/div[2]/comment-add-box/div[1]/div[1]/div[1]/a", "like");
            System.out.println("✅ Clicked on like for a post");

            Thread.sleep(6000);
            HelperFunctions.scrollAndClick(driver, "//*[@id=\"divList\"]/div/div[2]/div[1]/span[3]", "Popular");
            System.out.println("✅ Clicked on popular section");

            Thread.sleep(6000);
            HelperFunctions.scrollAndClick(driver, "//*[@id=\"divList\"]/div/div[2]/div[2]/ul/li[3]/div[3]/div[1]/div[2]/image-gallery/div/img[2]", "open_image");
            System.out.println("✅ Image opened successfully");

            Thread.sleep(2000);
            HelperFunctions.takeScreenshot(driver, "Image_opened");

            Thread.sleep(2000);
            driver.findElement(By.xpath("/html/body/div[6]/div[4]")).click();
            System.out.println("✅ Clicked on close image");

            Thread.sleep(4000);

            // File uploads
            String[] fileTypes = {"pdf", "image", "video"};
            String[] filePaths = {
                "C:\\Users\\Admin\\Downloads\\POM.pdf",
                "C:\\Users\\Admin\\Downloads\\SamplePNGImage_200kbmb.png",
                "C:\\Users\\Admin\\Downloads\\SampleVideo_1280x720_10mb.mp4"
            };

            WebElement uploadButton = driver.findElement(By.xpath("//*[@id=\"divList\"]/div/div[2]/comment-add-box/div[1]/div[1]/b/i"));
            uploadButton.click();
            Thread.sleep(4000);

            for (int i = 0; i < fileTypes.length; i++) {
                String type = fileTypes[i];
                String path = filePaths[i];

                System.out.println("🔄 Uploading " + type.toUpperCase() + " from " + path);

                switch (type) {
                    case "pdf" -> driver.findElement(By.xpath("//*[@id='rbnPDF']")).click();
                    case "image" -> driver.findElement(By.xpath("//*[@id='rbnImage']")).click();
                    case "video" -> driver.findElement(By.xpath("//*[@id='rbnvideo']")).click();
                    default -> throw new IllegalArgumentException("Invalid file type: " + type);
                }

                Thread.sleep(2000);

                WebElement browseButton = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[5]/div[3]/div/div[2]/div[5]/div[2]/img"));
                browseButton.click();
                Thread.sleep(3000);

                HelperFunctions.selectFile(path);
                System.out.println("✅ " + type.toUpperCase() + " selected");

                Thread.sleep(4000);

                WebElement descriptionBox = driver.findElement(By.xpath("//textarea[@name='Description']"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].style.pointerEvents = 'auto';", descriptionBox);

                String description = type.substring(0, 1).toUpperCase() + type.substring(1) + " upload is successful.";
                descriptionBox.clear();
                descriptionBox.sendKeys(description);

                HelperFunctions.takeScreenshot(driver, "After_" + type + "_Upload");
                System.out.println("📸 Screenshot taken after " + type + " upload");

                Thread.sleep(3000);
            }

            System.out.println("✅ Stories module test completed");

        } catch (InterruptedException e) {
            HelperFunctions.takeScreenshot(driver, "Stories_Unexpected_Error");
            HelperFunctions.logError("❌ Interrupted Error in StoriesPage: " + e.getMessage());
        } catch (AWTException | IllegalArgumentException e) {
            HelperFunctions.takeScreenshot(driver, "Stories_General_Error");
            HelperFunctions.logError("❌ General Error in StoriesPage: " + e.getMessage());
        }
    }
}