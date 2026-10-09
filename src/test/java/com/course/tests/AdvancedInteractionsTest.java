package com.course.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AdvancedInteractionsTest {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    // Actions — Selenium class for complex interactions
    // declared as field so all tests can use it

    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        // Actions needs driver to know which browser to act on
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    //Test 1 - Hover (move to Element)
    @Test(description = "Hover over element to reveal hidden menu")
    public void hoverTest(){
        driver.get("https://the-internet.herokuapp.com/hovers");

        By avatarLocator = By.cssSelector(".figure img");

        WebElement avatar = wait.until(ExpectedConditions.visibilityOfElementLocated(avatarLocator));
        actions.moveToElement(avatar).perform();
        // actions.moveToElement(element) → move mouse to element
        // .perform() → EXECUTES the action

        WebElement caption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
                (".figure .figcaption")));
        String captionText = caption.getText();
        System.out.println("Caption visible after hover: " + captionText);

        Assert.assertTrue(captionText.contains("name"),
                "Expected caption with name but got: " + captionText);
    }

    // Test 2: Right-click (context click)
    @Test(description = "Right click to see context menu")
    public void rightClickTest(){
        driver.get("https://the-internet.herokuapp.com/context_menu");

        WebElement hotSpot = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("hot-spot")));
        actions.contextClick(hotSpot).perform();

        // Handle the browser alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("Alert after right-click: " + alertText);
        alert.accept();

        Assert.assertTrue(alertText.contains("You selected a context menu"), "" +
                "Expected context menu but got: " + alertText);
    }

    //Test 3: Double click
    @Test(description = "Double click to trigger action")
    public void doubleClickTest(){
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        WebElement checkBox = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("input[type = 'checkbox']")));
        actions.doubleClick(checkBox).perform();
        System.out.println("Double click performed");
        System.out.println("Checkbox state: " + checkBox.isSelected());

        Assert.assertNotNull(checkBox);
        System.out.println("Double click test passed");
    }

    // Test 4: Drag & Drop
    @Test(description = "Drag element from source to target")
    public void dragAndDropTest(){
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        WebElement box1 = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("column-a"))); // element to drag from
        WebElement box2 = driver.findElement(By.id("column-b")); // element to drag to

        // Get text before dragging

        String sourceBefore = box1.getText();
        System.out.println("Befire drag - Column A: " + sourceBefore);

        actions.dragAndDrop(box1, box2).perform();

        // small pause to let animation complete
        try{ Thread.sleep(1000); } catch (Exception e) {}

        //get text after dragging
        String sourceAfter = box2.getText();
        System.out.println("After dragging: Column B " + sourceAfter);

        System.out.println("Drag & drop test completed");

    }

    // Test 5 - Keyboard combinations
    @Test(description = "Use keyboard shortcuts with actions")
    public  void keyboardActionsTest(){
        driver.get("https://www.google.com");

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.name("q")));
        actions.click(searchBox)
                .sendKeys("Selenium")
                .perform();
        System.out.println("Typed via actions");

        // select all text with Ctrl + a
        actions.keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .perform();
        // keyDown(Keys.CONTROL) → hold Ctrl
        // sendKeys("a")         → press A while Ctrl held
        // keyUp(Keys.CONTROL)   → release Ctrl

        System.out.println("Ctrl + a - all text selected");

        // Type new text - replaces selected text
        actions.sendKeys("Automation Testing").perform();
        String value = searchBox.getAttribute("value");
        System.out.println("Final Text: " + value);

        Assert.assertTrue(value.contains("Automation"),
                "Expected automation in text but got: " + value);
    }

    // Test 6: Chained Actions
    @Test(description = "Chain multiple actions in one perform call")
    public void chainedActionsTest(){
        driver.get("https://the-internet.herokuapp.com/key_presses");

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("target")));
        actions.click(input)
                .sendKeys("A")
                .perform();
        // chain: click -> type -> press enter -> all in one action/perform

        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        String resultText = result.getText();
        System.out.println("Key pressed result: " + resultText);

        Assert.assertTrue(resultText.contains("A"),
                "Expected A but got: " + resultText);

        // press ENTER separately
        actions.click(input)
                .sendKeys(Keys.RETURN)
                .perform();
        System.out.println("Chain Action test passed");
    }
}