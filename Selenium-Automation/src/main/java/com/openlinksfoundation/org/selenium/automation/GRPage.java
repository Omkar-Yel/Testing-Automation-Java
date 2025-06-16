/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.openlinksfoundation.org.selenium.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;

public class GRPage {

    private WebDriver driver;

    public GRPage(WebDriver driver) {
        this.driver = driver;
    }

    public void runGR() {
        try {
            Thread.sleep(4000);
            HelperFunctions.takeScreenshot(driver, "Before_Click_GR");
            Thread.sleep(2000);

            WebElement grItem = driver.findElement(By.xpath("//*[@id=\"rbnGR\"]"));
            grItem.click();
            System.out.println("✅ Clicked on GR item");

            Thread.sleep(4000);

            HelperFunctions.scrollAndClick(driver, "//*[@id=\"divFeed\"]/div/div[7]/div[18]/div[1]/a/small", "Chapter");
            System.out.println("✅ Clicked on Chapter");

            Thread.sleep(4000);
            WebElement period = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[34]/div[1]/div[2]/div[1]/div/div/div[2]/div/img"));
            period.click();
            System.out.println("✅ Clicked on Period");

            Thread.sleep(6000);
            WebElement PDFClick = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[1]/div/div[34]/div[2]/div[1]/div/div[3]/div[1]/div[1]/div[2]/img"));
            PDFClick.click();
            System.out.println("✅ Opened PDF");

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
            HelperFunctions.scrollAndClick(driver, "//*[@id=\"divFeed\"]/div/div[1]/div[1]/div/button[2]/span", "closeButton");
            System.out.println("✅ Clicked on Cross Button to exit GR page");

            System.out.println("✅ GR Test completed successfully");

        } catch (InterruptedException e) {
            HelperFunctions.takeScreenshot(driver, "Unexpected_Error");
            HelperFunctions.logError("❌ Unexpected Error in GRPage: " + e.getMessage());
        }
    }
}