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

public class FirstTest {

    WebDriver driver;

    @BeforeEach

    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //open browser maximised
        driver.manage().window().maximize();
    }

    @Test

    public void openGoogleAndSearch(){
        // 1. Navigate to Google
        driver.get("https://www.google.com");

        // 2. Print the page title to console
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);

        // 3. Assert the title contains google
        Assertions.assertTrue(title.contains("Google"), "Title should contain Google but was:" + title);

        // 4. Find the search box and type something
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium Java Automation");

        // 5. Press Enter to Search
        searchBox.submit();

        // 6. Print new page title
        System.out.println("Search results title: " + driver.getTitle());

    }

    @Test

    public void verifyGoogleTitle(){
        driver.get("https://www.google.com");

        String title = driver.getTitle();
        //Assert title is exactly "Google"
        Assertions.assertEquals("Google", title, "Expected title to be Google but got: " + title);
        System.out.println("✅ Title verified: " + title);

    }

    @AfterEach

    public void tearDown(){
        //Always close the browser after each test
        if (driver != null){
            driver.quit();
        }
    }

}
