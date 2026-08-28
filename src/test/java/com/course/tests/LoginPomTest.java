package com.course.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.course.pages.HomePage;
import com.course.pages.LoginPage;

public class LoginPomTest {

    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        // page objects created once, used across all com.course.tests
    }

    @AfterEach
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    @Test
    public  void successfulLoginTest(){
        loginPage.navigateTo();   // navigate
        loginPage.logIn("student", "Password123"); //login with valid credentials
        String heading = homePage.headingText();  //verify on home page
        System.out.println("Heading: " + heading);
        Assertions.assertEquals("Logged In Successfully", heading, "Expected success heading but got " + heading);

        String url = homePage.currentUrl(); // URL verification
        System.out.println("URL: " + url);
        Assertions.assertFalse(url.contains("practice-test-login"), "Should have left login page!");
    }

    @Test
    public void wrongPasswordTest(){
        loginPage.navigateTo();
        loginPage.logIn("student", "123");
        String error = loginPage.getErrormsg();
        System.out.println("Error message: " + error);
        Assertions.assertTrue(error.contains("Your password is invalid!"), "Expected password error but got: " + error);
    }

    @Test
    public void wrongUsernameTest(){
        loginPage.navigateTo();
        loginPage.logIn("abcd", "Password123");
        String error = loginPage.getErrormsg();
        System.out.println("Error message: " + error);
        Assertions.assertTrue(
                error.contains("Your username is invalid!"),
                "Expected username error but got: " + error
        );
    }

    @Test
    public void emptyFieldTest(){
        loginPage.navigateTo();
        loginPage.logIn("","");
        String error = loginPage.getErrormsg();
        System.out.println("Error message: " + error);
        Assertions.assertFalse(error.isEmpty(), "Expected some error for empty fields!");
    }
}
