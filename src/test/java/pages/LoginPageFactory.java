package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageFactory {
    private WebDriver driver;
    private WebDriverWait wait;

    //Locators using @FindBy - no need to call findElement anymore
    @FindBy(id="username")
    private WebElement usernameField;

    @FindBy(id="password")
    private WebElement passwordField;

    @FindBy(id="submit")
    private WebElement submitButton;

    @FindBy(id="error")
    private WebElement errorMsg;

    public LoginPageFactory(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
        // THIS is what makes @FindBy work
        // MUST be called in constructor — before any element is used
    }

    // Actions
    public void navigateTo(){
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    public void enterUsername(String username){
        wait.until(ExpectedConditions.visibilityOf(usernameField)); // visibilityOf() — takes WebElement directly
                                                                    // vs visibilityOfElementLocated() — takes By locator
                                                                    // use visibilityOf() with PageFactory elements
    usernameField.clear();
    usernameField.sendKeys(username);
    }

    public void enterPassword(String password){
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin(){
        submitButton.click();
    }

    public String getErrorMsg(){
        wait.until(ExpectedConditions.visibilityOf(errorMsg));
        return errorMsg.getText();
    }

    public void login(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

}
