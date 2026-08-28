package com.course;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

public class LoginTest {

    private static final Log log = LogFactory.getLog(LoginTest.class);
    WebDriver driver;
    WebDriverWait wait;

    static final String URL = "https://practicetestautomation.com/practice-test-login/";
    // static final = constant — value never changes
    // storing URL once so if it changes we update in one place only

    @BeforeEach

    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach

    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
    //--------------------------------------
    // Helper method — reusable login action
    //--------------------------------------
    private void login(String username, String password){   // private method — only used inside this class
                                                            // takes username and password as parameters
                                                            // reusable — called by multiple com.course.tests below
    driver.get(URL);
        // Wait for page to load then fill fields

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameField.clear();
        usernameField.sendKeys(username);  // username parameter passed in — different for each test

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordField.clear();
        passwordField.sendKeys(password);   // password parameter passed in for dynamic use

        driver.findElement(By.id("submit")).click();
        // ↑ click Login button
    }
    //--------------------------------------------
    //Helper method - takes screenshots on failure
    //--------------------------------------------

    private void takeScreenshots(String testName){  // takes a screenshot and saves it with the test name
        TakesScreenshot ts = (TakesScreenshot) driver; // TakesScreenshot is a Selenium interface
                                                       // ChromeDriver implements it — so we cast driver to it
        File screenshot = ts.getScreenshotAs(OutputType.FILE);
        // captures the current screen as a File object
        // OutputType.FILE → save as a file (vs bytes or base64)

        String path = "screenshots/" + testName + ".png";
        // ↑ save location — screenshots folder + test name + .png

        try{
            FileHandler.copy(screenshot,new File(path)); // copies screenshot from temp location to our path
            System.out.println("Screenshot saved: " + path);
        } catch (IOException e) {
            System.out.println("Screenshot failed: " + e.getMessage());;
        }
    }
    //-------------------------------
    // Test 1 — Successful login
    //-------------------------------
    @Test
    public void successfulLoginTest() {
        login("student", "Password123");

        // Wait for success heading
        WebElement successHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("post-title")
                )
        );

        // Save text immediately
        String headingText = successHeading.getText();
        System.out.println("✅ Success Heading: " + headingText);

        // Assert heading is not empty — page loaded after login
        Assertions.assertFalse(
                headingText.isEmpty(),
                "Heading should not be empty after login!"
        );

        // Assert heading contains exactly what the page shows

        Assertions.assertTrue(
                headingText.equals("Logged In Successfully"),
                // ↑ equals() checks EXACT match — no hidden character can hide
                "Expected 'Logged In Successfully' but got: '" + headingText + "'"
        );

        // Assert URL changed away from login page
        String currentUrl = driver.getCurrentUrl();
        System.out.println("✅ Current URL: " + currentUrl);

        Assertions.assertFalse(
                currentUrl.contains("practice-test-login"),
                // ↑ just verify we LEFT the login page
                // ↑ much simpler than checking the exact success URL
                "Should have navigated away from login page!"
        );

        System.out.println("✅ Login test passed!");
    }

    //----------------------
    // Test 2 — Failed login: wrong password
    //----------------------

    @Test
    public void wrongPasswordTest(){
        login("student","123");  //valid username + wrong password

        // Wait for error message to appear
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        String error = errorMsg.getText();
        System.out.println("Error message: " + error);

        //Verify correct error msg shown
        Assertions.assertTrue(error.contains("Your password is invalid!"), "Expected password error but got: " + error);

        //take screenshot
        takeScreenshots("wrongPasswordTest");
    }

    //-----------------------------
    // Test 3 -Failed login: wrong username
    //----------------------------

    @Test
    public void wrongUsernameTest(){
        login("stu", "Password123"); //wrong username + valid password

        //wait for error msg to appear
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        String usernameError = errorMessage.getText();
        System.out.println("Username error received: " + usernameError);

        //Verify correct error msg shown
        Assertions.assertTrue(usernameError.contains("Your username is invalid!"), "Expected username error but got: " + usernameError);

        //take sc
        takeScreenshots("wrongUsernameTest");
    }

    //---------------------------
    // Test 4 - empty fields
    //--------------------------

    @Test
    public void emptyFieldTest(){
        login("","");

        //wait for error msg
        WebElement emptyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        String emptyError = emptyField.getText();
        System.out.println("Empty Field error received: " + emptyError);

        //Verification of correct error
        Assertions.assertFalse(emptyError.isEmpty(), "Expected some error msg");

        //take sc
        takeScreenshots("emptyFieldTest");
    }


}
