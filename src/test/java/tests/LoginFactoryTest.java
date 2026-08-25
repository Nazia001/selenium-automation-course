package tests;

import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePageFactory;
import pages.LoginPageFactory;

public class LoginFactoryTest {
    WebDriver driver;
    LoginPageFactory loginPage;
    HomePageFactory homePage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPageFactory(driver);
        homePage = new HomePageFactory(driver);
        // PageFactory.initElements() called inside each constructor
    }

    @AfterEach
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    @Test

    public void successfulLoginTest(){
        loginPage.navigateTo();
        loginPage.login("student","Password123");
        String heading = homePage.getHeadingText();
        System.out.println("Heading: " + heading);

        Assertions.assertEquals("Logged In Successfully", heading,
                "Expected success heading but got: " + heading);

        System.out.println("Logout visible: "+ homePage.isLogoutVisible());
        Assertions.assertTrue(homePage.isLogoutVisible(), "Logout button should be visible after login!");
    }

    @Test
    public void wrongPasswordTest(){
        loginPage.navigateTo();
        loginPage.login("student","1234");

        String error = loginPage.getErrorMsg();
        System.out.println("Error message: " + error);
        Assertions.assertTrue(error.contains("Your password is invalid!"),
                "Expected error message but got: " + error);
    }

    @Test
    public void wrongUserNameTest(){
        loginPage.navigateTo();
        loginPage.login("stu", "Password123");

        String error = loginPage.getErrorMsg();
        System.out.println("Error msg: "+ error);
        Assertions.assertTrue(error.contains("Your username is invalid!"), "Expected error msg but got: " + error);
    }

    @Test
    public void emptyFieldTest(){
        loginPage.navigateTo();
        loginPage.login("","");

        String error = loginPage.getErrorMsg();
        System.out.println("Error message: " + error);
        Assertions.assertFalse(error.isEmpty(), "Expected some error for empty fields!");
    }
}
