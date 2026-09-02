package com.course.tests;

import com.course.pages.HomePageFactory;
import com.course.pages.LoginPageFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTest {
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

    // @DataProvider — supplies data to tests
    @DataProvider(name = "loginData")
    // @DataProvider marks this as a data supplier
    // name = "loginData" → test references this name

    public Object[][] loginData(){
        // [] [] - means one for row & one for column
        // row - each test run
        // column - parameters
        return new Object[][]{
                {"student",       "Password123",  true  },
                // row 1 - valid credentials, should pass

                {"student", "wrongPass", false},
                // row 2 - invalid pass, should fail

                {"wrongUsername", "Password123", false},
                //// row 3 — wrong username, should fail

                {" ", " ", false}
                // empty fields, should fail
        };
    }

    @Test(dataProvider = "loginData")

    public void loginDataDrivenTest(String username, String password, boolean shouldPass){
        // parameters match the columns in @DataProvider exactly
        // String username  → column 0
        // String password  → column 1
        // boolean shouldPass → column 2
        System.out.println("Testing with: " + username + " / " + password);
        loginPage.navigateTo();
        loginPage.login(username, password);

        if(shouldPass){
            // valid credentials — verify success
            String heading = homePage.getHeadingText();
            System.out.println("Success: " + heading);
            Assert.assertEquals(heading, "Logged In Successfully", "Expected success message: " + heading);

        }else{
            String error = loginPage.getErrorMsg();
            System.out.println("Error: " + error);
            Assert.assertFalse(error.isEmpty(), "Expected error message for: " + username);
        }
    }

    @DataProvider(name = "invalidLoginData")
    public Object [][] invalidLoginData(){
        return new Object[][] {
                {"student", "wrongpass", "Your password is invalid!" },
                {"wronguser", "Password123", "Your username is invalid!"},
                {" ", " ", "Your username is invalid!" }
        };
    }

    @Test(dataProvider = "invalidLoginData")
    public void invalidLoginTest(String username, String password, String expectedError){
        // 3 parameters matching 3 columns

        System.out.println("Testing invalid login: " + username);
        loginPage.navigateTo();
        loginPage.login(username, password);

        String actualError = loginPage.getErrorMsg();
        System.out.println("Expected: " + expectedError);
        System.out.println("Actual: " + actualError);
        Assert.assertTrue(actualError.contains(expectedError), "Error mismatch for user:" + username);
    }



}
