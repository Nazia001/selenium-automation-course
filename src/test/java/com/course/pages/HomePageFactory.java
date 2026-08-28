package com.course.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageFactory {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "post-title")
    private WebElement pageHeading;

    @FindBy(linkText = "Log out")
    private WebElement logoutButton;

    public HomePageFactory(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getHeadingText(){
        wait.until(ExpectedConditions.visibilityOf(pageHeading));
        return pageHeading.getText();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public boolean isLogoutVisible(){
        try {
            wait.until(ExpectedConditions.visibilityOf(logoutButton));
            return logoutButton.isDisplayed();
        } catch (Exception e) {
           return  false;
        }
    }
}
