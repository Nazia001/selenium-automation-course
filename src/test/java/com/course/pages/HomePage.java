package com.course.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    //Locators in Home Page
    private By pageHeading = By.className("post-title");
    private By logoutButton = By.linkText("Log out");
    // By.linkText() — finds a link by its exact visible text

    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String headingText(){
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
        return heading.getText();
    }

    public String currentUrl(){
        return driver.getCurrentUrl();
    }

    public boolean isLogoutButtonVisible(){
        try {
            WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
            return logout.isDisplayed();
        } catch (Exception e) {
            return false;
            // if element not found → return false instead of crashing
        }
    }
}
