package com.course;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.List;

public class ElementInteractionsTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach

    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach

    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    //Test 1 — Click a button & type into fields

    @Test

    public void loginTest(){
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // Find username field and type into it
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        username.clear();
        // ↑ clear() wipes any pre-filled text before typing

        username.sendKeys("student");
        // ↑ types "student" into the username field

        //find password field and type into it
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        password.clear();
        password.sendKeys("Password123");

        // Find login button and click it
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
        loginButton.click();
        // ↑ click() simulates a mouse click on the element

        // Verify successful login — check success message appears
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("post-title")));

        System.out.println("✅ Page after login: " + successMsg.getText());
        Assertions.assertTrue(successMsg.getText().contains("Logged In Successfully"),
                "Login failed! Message was: " + successMsg.getText());
    }

    //Test 2 - Checkbox

    @Test

    public void checkboxTest(){
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        // Find all checkboxes on the page using findElements (returns a list)

        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));

        // ↑ findElements() returns ALL matching elements as a List
        // ↑ index 0 = first checkbox, index 1 = second checkbox

        WebElement checkbox1 = checkboxes.get(0);
        //fetch first one

        WebElement checkbox2 = checkboxes.get(1);
        //fetch the second one

        // Check if it's already selected
        System.out.println("Checkbox 1 selected before click: " + checkbox1.isSelected());
        //isSelected() returns true if checked, false if unchecked

        // Click it to change its state
        checkbox1.click();

        // Verify state changed (if it's really clicked)
        System.out.println("Checkbox 1 selected after click: " + checkbox1.isSelected());

        Assertions.assertTrue(checkbox1.isSelected(), "Checkbox should be checked after clicking");

    }

    //Test 3 - Dropdown (Select class)

    @Test

    public void dropdownTest(){
        driver.get("https://the-internet.herokuapp.com/dropdown");

        //find the dropdown element
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown")));

        //Select class - gives us dropdown-specific methods
        Select dropdown = new Select(dropdownElement);
        // Select class only works with <select> HTML tags
        // ↑ gives you 3 ways to pick an option:

        // Option A — select by visible text
        dropdown.selectByVisibleText("Option 1");
        System.out.println("✅ Selected by text:" + dropdown.getFirstSelectedOption().getText());
        // ↑ getFirstSelectedOption() returns currently selected option
        // ↑ .getText() gets its visible text

        // Option B — select by value attribute
        dropdown.selectByValue("2");
        System.out.println("✅ Selected by value:" + dropdown.getFirstSelectedOption().getText());

        // Option C — select by index (0 = first option)
        dropdown.selectByIndex(1);
        System.out.println("✅ Selected by index:" + dropdown.getFirstSelectedOption().getText());

        Assertions.assertEquals("Option 1", dropdown.getFirstSelectedOption().getText());
    }

    //Test 4 — Radio buttons

    @Test

    public void radioButtonTest(){
        driver.get("https://demoqa.com/radio-button");

        // The page has 3 radio buttons:
        // "Yes", "Impressive", "No" (No is disabled)

        // Find the "Yes" radio button by id
        WebElement yesRadio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("yesRadio")));
        // ↑ presenceOfElementLocated not visibilityOf
        // ↑ because the input circle itself is visually hidden
        // ↑ but it IS present in the HTML — we need presence not visibility

        // Click Yes radio butto
        yesRadio.click();

        // Verify Yes is now selected
        System.out.println("Yes selected: " + yesRadio.isSelected());
        Assertions.assertTrue(yesRadio.isSelected(),"Yes radio should be selected!");

        //Find the "Impressive" radio button by id
        WebElement impressiveRadio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("impressiveRadio")));

        //click Impressive radio
        impressiveRadio.click();

        //Verify impressive is selected
        System.out.println("Impressive selected: " + impressiveRadio.isSelected());
        Assertions.assertTrue(impressiveRadio.isSelected(), "Impressive should be selected!");
        Assertions.assertFalse(yesRadio.isSelected(), "Yes should be deselected and impressive selected");


        //find no radio
        WebElement noRadio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("noRadio")));


        //Verify no radio is disabled
        Assertions.assertFalse(noRadio.isEnabled(), "No Radio should be disabled");
        System.out.println("radio button test passed!");


    }

    //Test 5 - Alert Handling

    @Test

    public void alertTest(){
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // Click button that triggers a simple alert
        WebElement alertButton = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[text()='Click for JS Alert']")));

        alertButton.click();
        // ↑ This opens a browser alert popup

        //Switch to the alert
        Alert alert = driver.switchTo().alert();
        // ↑ driver.switchTo().alert() → moves focus to the alert popup
        // ↑ Without this, you can't interact with the alert

        //Read alert Text
        String alertText = alert.getText();
        System.out.println("Alert Text: " + alertText);

        //Accept the alert (click ok)
        alert.accept();
        // ↑ accept() = click OK button on alert
        // ↑ dismiss() = click Cancel button on alert

        //Verify result message
        WebElement result = driver.findElement(By.id("result"));
        System.out.println("Result: " + result.getText());

        Assertions.assertEquals("I am a JS Alert", alertText);
    }


}
