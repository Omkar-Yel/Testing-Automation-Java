/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.openlinksfoundation.org.selenium.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class TLMPage {

    private WebDriver driver;

    public TLMPage(WebDriver driver) {
        this.driver = driver;
    }

    public void runTLM() {
        try {
            Thread.sleep(3000);
            HelperFunctions.takeScreenshot(driver, "Before_Click_TLM");

            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[2]/div[2]/div/div[1]/div/i", "TLM");
            System.out.println("✅ Clicked on TLM Page");

            Thread.sleep(5000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[1]/div[2]/div/div/ul/li[1]/ul/li[12]/div/div[2]/span/i", "Subject");
            System.out.println("✅ Clicked on Subject");

            Thread.sleep(6000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/a/small", "Chapter");
            System.out.println("✅ Clicked on Chapter");

            Thread.sleep(7000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[2]/div[1]/div[2]/div/div[2]/div[3]/div[3]/div[1]/div[1]/div[2]/img", "Period");
            System.out.println("✅ Clicked on Period");

            Thread.sleep(6000);
            Actions actions = new Actions(driver);
            actions.moveByOffset(150, 200).click().perform();
            System.out.println("✅ Clicked on media");

            Thread.sleep(20000);
            HelperFunctions.takeScreenshot(driver, "media_accessed_successfully");

            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[2]/div[8]/div[1]/div/div[2]/div[1]/p/a/i", "Back from media");
            System.out.println("✅ Back from media page");

            Thread.sleep(2000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[1]/i", "Back from period");
            System.out.println("✅ Back from period page");

            Thread.sleep(2000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[3]/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/i", "Back from chapter");
            System.out.println("✅ Back from chapter page");

            Thread.sleep(2000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[1]/div/div[1]/div/span/i", "Home");
            System.out.println("✅ Home button clicked");

            Thread.sleep(4000);
            HelperFunctions.scrollAndClick(driver, "/html/body/div[4]/div[1]/div/div[2]/i[6]", "Logout");
            System.out.println("✅ Logout button clicked");

            System.out.println("✅ TLM Test completed successfully");

        } catch (InterruptedException e) {
            HelperFunctions.takeScreenshot(driver, "Unexpected_Error");
            HelperFunctions.logError("❌ Unexpected Error in TLMPage: " + e.getMessage());
        } catch (Exception e) {
            HelperFunctions.takeScreenshot(driver, "General_Error");
            HelperFunctions.logError("❌ General Exception in TLMPage: " + e.getMessage());
        }
    }
}