/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.openlinksfoundation.org.selenium.automation;


import org.openqa.selenium.*;
import java.io.File;
import java.util.List;

public class SanityTestingPage {
    private WebDriver driver;

    public SanityTestingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void runSanityTest() throws InterruptedException {
        HelperFunctions.takeScreenshot(driver, "login_successful");
        Thread.sleep(4000);

        checkFeedSection();
        checkLeaderboardSection();

        System.out.println("✅ Sanity Test completed successfully");
    }

    private void checkFeedSection() {
        String baseFeedFolder = "SanityTest/Feed/";
        new File(baseFeedFolder + "Pass").mkdirs();
        new File(baseFeedFolder + "Fail").mkdirs();

        List<WebElement> feedPosts = driver.findElements(
            By.xpath("//div[contains(@class, 'd-flex') and contains(@ng-click, 'GoToUserPortfolio')]")
        );

        String feedResult = (!feedPosts.isEmpty()) ? "Pass" : "Fail";
        String feedShotName = (!feedPosts.isEmpty()) ? "feed_has_posts" : "feed_blank";
        HelperFunctions.takeScreenshot(driver, baseFeedFolder + feedResult + "/" + feedShotName);
        System.out.println("📸 Feed screenshot saved in " + feedResult + " folder");
    }

    private void checkLeaderboardSection() {
        try {
            // Open menu
            WebElement menuIcon = driver.findElement(By.xpath("//*[@id='divFeed']/div/div[3]/i[2]"));
            menuIcon.click();
            Thread.sleep(3000);

            // Click leaderboard circle
            WebElement leaderboardCircle = driver.findElement(
                By.xpath("//*[@id='divFeed']/div/div[3]/div[3]/div[1]/div[2]/div/div/div[2]/p[1]")
            );
            leaderboardCircle.click();
            Thread.sleep(3000);

            String baseLeaderboardFolder = "SanityTest/Leaderboard/";
            new File(baseLeaderboardFolder + "Pass").mkdirs();
            new File(baseLeaderboardFolder + "Fail").mkdirs();

            List<WebElement> leaderboardData = driver.findElements(
                By.xpath("//div[contains(@class,'lb-name')]")
            );

            String lbResult = (!leaderboardData.isEmpty()) ? "Pass" : "Fail";
            String lbShotName = (!leaderboardData.isEmpty()) ? "leaderboard_data" : "leaderboard_blank";
            HelperFunctions.takeScreenshot(driver, baseLeaderboardFolder + lbResult + "/" + lbShotName);
            System.out.println("📸 Leaderboard screenshot saved in " + lbResult + " folder");

        } catch (Exception e) {
            System.out.println("⚠️ Error while checking Leaderboard: " + e.getMessage());
            HelperFunctions.takeScreenshot(driver, "SanityTest/Leaderboard/Fail/leaderboard_error");
        }
    }
}