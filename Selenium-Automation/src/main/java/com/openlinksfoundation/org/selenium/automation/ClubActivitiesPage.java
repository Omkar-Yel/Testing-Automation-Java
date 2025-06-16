/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.openlinksfoundation.org.selenium.automation;



import org.openqa.selenium.*;
import java.awt.*;

public class ClubActivitiesPage {

    private WebDriver driver;

    public ClubActivitiesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void runClubActivities() throws AWTException {
        try {
            HelperFunctions.takeScreenshot(driver, "Before_Click_Club_Activities");

            driver.findElement(By.xpath("//*[@id=\"divFeed\"]/div/div[1]/div[1]/button/span[2]")).click();
            System.out.println("✅ Clicked on Add teacher story item");

            Thread.sleep(3000);

            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[1]/div[2]/select[1]", "language");
            Thread.sleep(2000);

            driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[1]/div[2]/select[2]")).click();
            System.out.println("✅ Clicked on class item");

            Thread.sleep(2000);

            driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[1]/div[3]/div/a/span[2]")).click();
            System.out.println("✅ Clicked on Club name item");

            HelperFunctions.scrollAndClick(driver, "//*[@id=\"ui-select-choices-row-0-1\"]/div/div[2]", "Katha");

            Thread.sleep(3000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[2]/div/div/div[2]/div[3]/button/span[2]", "Upload");

            Thread.sleep(3000);
            driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[2]/div[2]/div/ui-carousel/div/div[1]/div/div[2]/div/div[1]/a/img")).click();
            System.out.println("✅ Clicked on media select item");

            Thread.sleep(3000);
            HelperFunctions.selectFile("C:\\Users\\Admin\\Downloads\\peter-vanosdall-uZVtAixV8c8-unsplash.jpg");

            Thread.sleep(4000);

            WebElement descBox = driver.findElement(By.xpath("//textarea[@name='Description']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.pointerEvents = 'auto';", descBox);
            descBox.sendKeys("Image upload in club activities is successful.");
            HelperFunctions.takeScreenshot(driver, "Description_Field_Entered");

            Thread.sleep(1000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[4]/div[1]/div/div/form/div[7]/button", "save");
            Thread.sleep(3000);
            HelperFunctions.takeScreenshot(driver, "media is saved");

            Thread.sleep(10000);

            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[6]/div[1]/div/div[2]/div[1]/p/a/i", "Back Button");

            Thread.sleep(3000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[1]/div/div[3]/div[2]/div[2]/div/div/div[2]/div[3]/button/span[2]", "Upload");

            driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[2]/div[2]/div/ui-carousel/div/div[1]/div/div[1]/div/div[2]/a/img")).click();
            System.out.println("✅ Clicked on pdf select item");

            Thread.sleep(3000);
            HelperFunctions.selectFile("C:\\Users\\Admin\\Downloads\\Refurbished laptop.pdf");

            Thread.sleep(4000);

            WebElement descBox2 = driver.findElement(By.xpath("//textarea[@name='Description']"));
            js.executeScript("arguments[0].style.pointerEvents = 'auto';", descBox2);
            descBox2.sendKeys("PDF upload in club activities is successful.");
            HelperFunctions.takeScreenshot(driver, "Description_Field_Entered");

            Thread.sleep(1000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[12]/div[4]/div[1]/div/div/form/div[7]/button", "save");

            Thread.sleep(3000);
            HelperFunctions.takeScreenshot(driver, "media is saved");

            System.out.println("✅ Media upload test is completed");

        } catch (InterruptedException e) {
            HelperFunctions.logError("❌ Interrupted Exception in ClubActivities: " + e.getMessage());
        } catch (Exception e) {
            HelperFunctions.logError("❌ General Exception in ClubActivities: " + e.getMessage());
            HelperFunctions.takeScreenshot(driver, "ClubActivities_Error");
        }
    }
}