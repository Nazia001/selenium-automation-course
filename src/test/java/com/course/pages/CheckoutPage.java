package com.course.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "add_btn")
    private WebElement addButton;

    @FindBy(xpath = "(//button[@id= 'save_btn']) [2]")
    private WebElement saveButton;

    @FindBy (xpath = "(//input[@type= 'text']) [2]")
    private WebElement secondRowInput;

    @FindBy (id = "confirmation")
    private WebElement confirmMsg;

    public CheckoutPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateTo(){
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }

    public void clickAdd(){
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addButton.click();
    }

    public void typeInSecondRow(String text){
        wait.until(ExpectedConditions.visibilityOf(secondRowInput));
        secondRowInput.clear();
        secondRowInput.sendKeys(text);
    }

    public  void clickSave(){
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    public String getConfirmMsg(){
        wait.until(ExpectedConditions.visibilityOf(confirmMsg));
        return confirmMsg.getText();
    }

    public String getPageTitle(){
        return driver.getTitle();
    }


}
