package com.course.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    //Locators defined
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By submitButton = By.id("submit");
    private By errorMsg = By.id("error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

        public void navigateTo(){
            driver.get("https://practicetestautomation.com/practice-test-login/");
        }

        public void enterUsername(String username){
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            field.clear();
            field.sendKeys(username);
        }

        public void enterPassword(String password){
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        }

        public void clickLogin(){
        driver.findElement(submitButton).click();
        }

        public String getErrormsg(){
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));
        return error.getText();
        }

        public void logIn(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickLogin();
            // test just calls login() without knowing the steps
        }
    }


