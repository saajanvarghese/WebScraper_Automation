package demo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilities {

    public static void scrape(WebDriver driver, String year){
        //Locate year LinkElement
        WebElement yearLink = driver.findElement(By.id(year));
        // Convert year LinkElement to String 
        String yearLinktxt = yearLink.getText();
        // Click YearLinkElement
        SeleniumWrapper.clickAction(yearLink, driver);

        // Explicit wait to load the table after clicking a year
        WebDriverWait wait2015 = new WebDriverWait(driver, Duration.ofSeconds(30));

        // wait until the table is visible
        wait2015.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table']")));

        // Initialize and declare a HashMap ArrayList called movieList
        ArrayList<HashMap<String, String>> movieList = new ArrayList<>();

        // Declare epochTime
        long epoch = System.currentTimeMillis()/1000;

        // Convert epochTime to String
        String epochtime = String.valueOf(epoch);

            // Iterate through each row of the table
           List<WebElement> rows = driver.findElements(By.xpath("//tr[@class='film']"));
           // Declare int count = 0;
           int count = 0;
           for (WebElement row : rows) {
               // Extract data from each row
               //Get text from movieTitle Locator
               String movieTitle = row.findElement(By.xpath("./td[@class='film-title']")).getText();
               //Get text from nominations Locator and convert it to int using Integer.parseInt();
               int nominations = Integer.parseInt(row.findElement(By.xpath("./td[@class='film-nominations']")).getText());
               //Get text from awards Locator and convert it to int using Integer.parseInt();
               int awards = Integer.parseInt(row.findElement(By.xpath("./td[@class='film-awards']")).getText());

               // declare boolean isWinner = true, when count == 0; , so the first element will be true and others = false.
               boolean isWinner = count == 0; 
               // convert the boolean value to String
               String isWinnerText = String.valueOf(isWinner);
               // Create a HashMap to store the data
                   HashMap<String, String> dataMap = new HashMap<>();
                   dataMap.put("epoch Time", epochtime);
                   dataMap.put("Year", yearLinktxt);
                   dataMap.put("Title", movieTitle);
                   dataMap.put("Nominations", String.valueOf(nominations));
                   dataMap.put("Awards", String.valueOf(awards));
                   dataMap.put("isWinner", isWinnerText);
                   
                   // Add the HashMap to the ArrayList
                   movieList.add(dataMap);
                   // iterate the count variable by 1
                   count++;

                   // check if count is >= 5, if so break the loop, else continue till it reaches >=5.
                   if(count >= 5){
                     break;
                   }
           }

           //Print the collected data
           for (HashMap<String, String> data : movieList) {
            System.out.println("Epoch Time of Scrape: " + data.get("epoch Time")+
                ", Year " + data.get("Year") +
                    ", Title: " + data.get("Title") +
                    ", Nominations: " + data.get("Nominations")+
                    ", Awards: "+ data.get("Awards")+ 
                    ", isWinner: " + data.get("isWinner"));
        }

        // Store the HashMap Data in a json File
        ObjectMapper objectMapper2015 = new ObjectMapper();
        try {
            File jsonFile = new File("D:/Automation Crio projects/Crio-Code-A-Thon Assignments/WebScraper/selenium-starter-2/src/test/resources/movieList"+year+"-data.json");
            objectMapper2015.writeValue(jsonFile, movieList);
            System.out.println("JSON data written to: " + jsonFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}