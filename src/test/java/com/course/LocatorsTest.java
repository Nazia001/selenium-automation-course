package com.course;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LocatorsTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach

    public void setUp(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //wait for 10 sec to locate the elements

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @AfterEach
    public void tearDown(){
        if (driver!= null){
            driver.quit();
        }
    }

    @Test

    public void findById() {
        // Using a stable practice site instead of Google
        driver.get("https://www.google.com");

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("APjFqb")));

        //type into the search box
        searchBox.sendKeys("Selenium Java automation");

        //verify text was typed correctly
        String typedText = searchBox.getAttribute("value");

        System.out.println("✅ Found by ID, typed: " + typedText);

        Assertions.assertEquals("Selenium Java automation", typedText);
    }

    @Test

    public void findByName(){
        driver.get("https://www.google.com");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        searchBox.sendKeys("Selenium Java automation");

        String typedText = searchBox.getAttribute("value");

        System.out.println("✅ Found element by Name: " + typedText);

        Assertions.assertEquals("Selenium Java automation", typedText);
    }

    @Test

    public void findByClass(){
        driver.get("https://www.google.com");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".gLFyf")));
        searchBox.sendKeys("Selenium Java automation");

        String typedText = searchBox.getAttribute("value");

        System.out.println("✅ Found element by Class: " + typedText);

        Assertions.assertEquals("Selenium Java automation", typedText);

    }

    @Test

    public void findByCssSelector(){
        driver.get("https://www.google.com");

        // CSS selector using name attribute
        // pattern: tag[attribute='value']
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea[name = 'q']")));

        searchBox.sendKeys("Selenium Java automation");
        String typedText = searchBox.getAttribute("value");

        System.out.println("✅ Found by CSS Selector, typed: " + typedText);

        Assertions.assertEquals("Selenium Java automation", typedText);
    }

    @Test

    public void findByXpath(){
        driver.get("https://www.google.com");

        //find search box by xpath attribute
        // XPath using name attribute
        // // means anywhere on page
        // pattern ("//tag[@attribute = 'value']")

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//textarea[@name='q']")));

       searchBox.sendKeys("Selenium Java automation");
       String typedText = searchBox.getAttribute("value");

        // Also find the Google Search button by its value attribute
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable
                        (By.xpath("//input[@value = 'Google Search']")));

        System.out.println("✅ Found element by XPath: " + typedText);
        System.out.println("✅ Search button found: " + searchButton.getAttribute("value"));

        Assertions.assertEquals("Selenium Java automation", typedText);
    }
}

