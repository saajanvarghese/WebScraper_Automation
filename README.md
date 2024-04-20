# Web Scraper Automation

# Description
The Application Under Test is https://www.scrapethissite.com/pages/.

The Aim of this project is to scrape the data from this website and store it in a HashMap and convert the HashMap data to a json file.

This Web Scraper Automation project consists of 2 Testcases in Total.

# TestCase 01 Description
Go to https://www.scrapethissite.com/pages/.  Check the Current URL using Assert Statements.

1. Iterate through the table and collect the Team Name, Year and Win % for the teams with Win % less than 40% (0.40).

2. Iterate through 4 pages of this data and store it in a ArrayList<HashMap>.

3. Convert the ArrayList<HashMap> object to a JSON file named `hockey-team-data.json`. Feel free to use Jackson library.
Each Hashmap object should contain: 

. Epoch Time of Scrape
. Team Name
. Year
. Win %

4. Store the file in the output folder in the root directory. Assert using TestNG that the file is present and not empty.

# TestCase 02 Description
1. Go to https://www.scrapethissite.com/pages/ and click on  “Oscar Winning Films”.

2. Click on each year present on the screen and find the top 5 movies on the list - store in an ArrayList<HashMap>. 

3. Keep a Boolean variable “isWinner” which will be true only for the best picture winner of that year.

4. Keep a variable to maintain the year from which the data is scraped.

5. Convert the ArrayList<HashMap> object to a JSON file named `oscar-winner-data.json`. 
Each HashMap object should contain:

. Epoch Time of Scrape
. Year
. Title
. Nomination
. Awards
. isWinner

6. Store the file in the output folder in the root directory. Assert using TestNG that the file is present and not empty.

# Note on This Web Scraper Automation Project
1. Added Selenium Wrapper Methods
2. Added TestNG
3. Implemented Assert statements

## Required Software to Install:
```
# java version 17
java --version
```
```
# git version 2.43.0
git --version
```
```
# gradle version 8.6
gradle --version
```
```
# vscode 1.88.1
git --version
```
## Required Dependency to run:
```
# WebDriverManager
# Selenium
# TestNG
# jackson-dataformat-xml
```
## Instructions to Run the Code:
```
# to build the project
gradle build
```
```
# to run the project
gradle run
```
```
# to test the project
gradle test
```