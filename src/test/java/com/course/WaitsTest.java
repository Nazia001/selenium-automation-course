package com.course;

import com.google.common.base.Function;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitsTest {

    WebDriver driver;

    @BeforeEach

    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test

    public void threadSleepTest() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // Click the Start button
        driver.findElement(By.cssSelector("#start button")).click();
        //parent child pattern element : 'button' tag inside the element with 'id = finish'

        Thread.sleep(5000);
        //always waits for 5 second even if it finds the element faster or later
        //wastes time or might never find the element
        //not a good approach for real projects

        WebElement hello = driver.findElement(By.cssSelector("#finish h4"));
        //parent child 'h4' tag inside the element 'id = finish'
        System.out.println("Text found: " + hello.getText());

        Assertions.assertTrue(hello.getText().contains("Hello"), "Expected Hello World text!");
        System.out.println("Thread.sleep wait worked but it's a bad approach");
    }

    @Test

    // ImplicitWait — set it ONCE, applies to ALL findElement() calls
    public void implicitWait() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // tells Selenium: if element not found immediately,
        // keep retrying for up to 10 seconds before throwing exception
        // applies globally to every findElement() in this driver session

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        driver.findElement(By.cssSelector("button")).click(); // click the 'start' button
        // css selector - 'tag name' used

        // ImplicitWait finds the element but text may still be empty
        // So we add an explicit check for text to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait specifically until #finish h4 has text in it
        WebElement hello = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish h4")));
        // Selenium retries finding #finish every 500ms for up to 10 seconds
        // as soon as it appears → moves to next line immediately

        //wait until text is present
        wait.until(ExpectedConditions.textToBePresentInElement(hello, "Hello"));
        // ↑ keeps checking until the element contains "Hello"
        // ↑ this is the most reliable way for dynamically loaded text


        System.out.println("Implicit wait found: " + hello.getText());
        Assertions.assertTrue(hello.getText().contains("Hello"), "Expected Hello text but got: " + hello
                .getText());
    }

    @Test

    public void explicitWait() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // ExplicitWait — waits for a SPECIFIC condition on a SPECIFIC element
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // find & click 'start' button
        driver.findElement(By.cssSelector("button")).click();

        // Wait until the finish element is VISIBLE specifically
        WebElement hello = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        // keeps checking every 500ms
        // as soon as #finish is visible → returns the element immediately
        // if still not visible after 10s → throws TimeoutException

        System.out.println("Explicit Wait Found: " + hello.getText());
        Assertions.assertTrue(hello.getText().contains("Hello"));


    }

    @Test

    // Different Expected Conditions

    public void diffConditionsTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        //** Condition 1 — elementToBeClickable
        // waits until button exists AND is visible AND is enabled
        WebElement startButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#start button")));
        // safest way to find a button before clicking
        // checks visible + enabled in one condition
        startButton.click();
        System.out.println("Start button clicked");

        //** Condition 2 - visibilityOfElementLocated
        // waits until element exists in HTML AND is visible on screen

        WebElement loadingBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loading")));

        System.out.println("Loading Bar is visible: " + loadingBar.isDisplayed());

        //**Condition 3 - invisibilityOfElementLocated
        //  waits until loading bar DISAPPEARS

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        // wait for spinner/loader to disappear before acting
        System.out.println("loading bar disappeared");

        //** Condition 4 — visibilityOfElementLocated for result
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish h4")));

        String resultText = result.getText();
        // this additional line saves the element into 'String'
        // Will not need to fetch the element later rather will pick it from here

        System.out.println("Result :" + result.getText());

        //** Condition 5 — titleContains
        // useful to wait for page navigation to complete
        driver.get("https://www.google.com");
        wait.until(ExpectedConditions.titleContains("Google"));
        System.out.println("Title contains Google: " + driver.getTitle());

        Assertions.assertTrue(resultText.contains("Hello"), "Expected Hello but got: " + resultText);


    }

    @Test

    public void fluentWait() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        driver.findElement(By.cssSelector("#start button")).click();

        WebElement hello = fluentWait.until(new Function<WebDriver, WebElement>() {
                                                @Override
                                                public WebElement apply(WebDriver driver) {
                                                    WebElement element = driver.findElement(By.cssSelector("#finish h4"));
                                                    if (element.isDisplayed() && !element.getText().isEmpty()) {
                                                        return element;
                                                        // return element only when visible AND has text
                                                    }
                                                    return null;
                                                    // return null to keep polling every 500ms
                                                }
                                            }
        );
        System.out.println("Fluent Wait Found: " + hello.getText());
        Assertions.assertTrue(hello.getText().contains("Hello"), "Expected Hello but got: " + hello
                .getText());
    }
}


