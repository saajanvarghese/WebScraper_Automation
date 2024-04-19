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
        WebElement year2015 = driver.findElement(By.id(year));
        String year2015txt = year2015.getText();
        SeleniumWrapper.clickAction(year2015, driver);

        WebDriverWait wait2015 = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait2015.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table']")));

        ArrayList<HashMap<String, String>> movieList2015 = new ArrayList<>();

            // Iterate through each row of the table
           List<WebElement> rows2015 = driver.findElements(By.xpath("//tr[@class='film']"));
           int count2015 = 0;
           for (WebElement row : rows2015) {
               // Extract data from each row
               String movieTitle = row.findElement(By.xpath("./td[@class='film-title']")).getText();
               int nominations = Integer.parseInt(row.findElement(By.xpath("./td[@class='film-nominations']")).getText());
               int awards = Integer.parseInt(row.findElement(By.xpath("./td[@class='film-awards']")).getText());

               boolean isWinner = count2015 == 0; 
               String isWinnerText = String.valueOf(isWinner);
               // Create a HashMap to store the data
                   HashMap<String, String> dataMap = new HashMap<>();
                   dataMap.put("Year", year2015txt);
                   dataMap.put("Title", movieTitle);
                   dataMap.put("Nominations", String.valueOf(nominations));
                   dataMap.put("Awards", String.valueOf(awards));
                   dataMap.put("isWinner", isWinnerText);
                   
                   // Add the HashMap to the ArrayList
                   movieList2015.add(dataMap);
                   count2015++;

                   if(count2015 >= 5){
                     break;
                   }
           }

           for (HashMap<String, String> data : movieList2015) {
            System.out.println("Year " + data.get("Year") +
                    ", Title: " + data.get("Title") +
                    ", Nominations: " + data.get("Nominations")+
                    ", Awards: "+ data.get("Awards")+ 
                    ", isWinner: " + data.get("isWinner"));
        }

        ObjectMapper objectMapper2015 = new ObjectMapper();
        try {
            File jsonFile = new File("D:/Automation Crio projects/Crio-Code-A-Thon Assignments/WebScraper/selenium-starter-2/src/test/resources/movieList"+year+"-data.json");
            objectMapper2015.writeValue(jsonFile, movieList2015);
            System.out.println("JSON data written to: " + jsonFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
