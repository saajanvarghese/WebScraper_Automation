package demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01() throws InterruptedException{
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.scrapethissite.com/pages/");

        Assert.assertTrue(driver.getCurrentUrl().equals("https://www.scrapethissite.com/pages/"), "Unverified URL");
        System.out.println("Verified URL: https://www.scrapethissite.com/pages/");

        WebElement HockeyTeamsLink = driver.findElement(By.linkText("Hockey Teams: Forms, Searching and Pagination"));

        SeleniumWrapper.clickAction(HockeyTeamsLink, driver);

        Thread.sleep(3000);

         ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

         WebElement clickPageButton = driver.findElement(By.xpath("//a[@aria-label='Next']"));
         clickPageButton.click();

         // Iterate through 4 pages
         for (int page = 1; page <= 4; page++) {
             // Iterate through each row of the table
            List<WebElement> rows = driver.findElements(By.xpath("//tr[@class='team']"));
            for (WebElement row : rows) {
                // Extract data from each row
                String teamName = row.findElement(By.xpath("./td[@class='name']")).getText();
                int year = Integer.parseInt(row.findElement(By.xpath("./td[@class='year']")).getText());
                double winPercentage = Double.parseDouble(row.findElement(By.xpath("./td[contains(@class, 'pct') and (contains(@class, 'text-success') or contains(@class, 'text-danger'))]")).getText());

                // Check if win percentage is less than 40%
                if (winPercentage < 0.4) {
                    // Create a HashMap to store the data
                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("TeamName", teamName);
                    dataMap.put("Year", String.valueOf(year));
                    dataMap.put("WinPercentage", String.valueOf(winPercentage));
                    
                    // Add the HashMap to the ArrayList
                    dataList.add(dataMap);
                }
            }

            // Navigate to the next page
            if (page < 4) {
             WebElement nextPageButton = driver.findElement(By.xpath("//a[@aria-label='Next']"));
                nextPageButton.click();

                // You might need to add some wait here to ensure the page is fully loaded before scraping again
                Thread.sleep(5000);
            }
        }

        // Print the collected data
        for (HashMap<String, String> data : dataList) {
            System.out.println("Team Name: " + data.get("TeamName") +
                    ", Year: " + data.get("Year") +
                    ", Win Percentage: " + data.get("WinPercentage"));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File("D:/Automation Crio projects/Crio-Code-A-Thon Assignments/WebScraper/selenium-starter-2/src/test/resources/hockey-team-data.json");
            objectMapper.writeValue(jsonFile, dataList);
            System.out.println("JSON data written to: " + jsonFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end Test case: testCase01");
    }
}
