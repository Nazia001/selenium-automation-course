package com.course.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.course.pages.HomePageFactory;
import com.course.pages.LoginPageFactory;

public class LoginTestng {
    WebDriver driver;
    LoginPageFactory loginPage;
    HomePageFactory homePage;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPageFactory(driver);
        homePage = new HomePageFactory(driver);
    }
    @AfterMethod
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Test (priority = 1, description = "Verify successful login with valid credentials")

    public void successfulLoginTest(){
        loginPage.navigateTo();
        loginPage.login("student","Password123");

        String heading = homePage.getHeadingText();
        System.out.println("Heading: " + heading);

        Assert.assertEquals(heading, "Logged In Successfully", "Expected success heading");
        // TestNG Assert — note parameter ORDER is different from JUnit!
        // JUnit:  assertEquals(expected, actual)
        // TestNG: assertEquals(actual, expected) ← actual FIRST
    }

    @Test (priority = 2, description = "wrong password test")
    public void wrongPasswordTest(){
        loginPage.navigateTo();
        loginPage.login("student","123");

        String error = loginPage.getErrorMsg();
        System.out.println("Error: " + error);

        Assert.assertTrue(error.contains("Your password is invalid!"),"Expected password error!");
    }

    @Test (priority = 3, description = "wrong username test")

    public void wrongUsernameTest(){
        loginPage.navigateTo();
        loginPage.login("st","Password123");

        String error = loginPage.getErrorMsg();
        System.out.println("Error: " + error);
        Assert.assertTrue(error.contains("Your username is invalid!"), "Expected username error!");
    }

    @Test (priority = 4, description = "Empty Credentials Test")

    public void emptyFieldsTest(){
        loginPage.navigateTo();
        loginPage.login("","");

        String error = loginPage.getErrorMsg();
        System.out.println("Error: " + error);

        Assert.assertFalse(error.isEmpty(), "Expected some error for empty fields!");
    }
}
