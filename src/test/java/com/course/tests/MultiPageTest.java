package com.course.tests;

import com.course.pages.CheckoutPage;
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

public class MultiPageTest {

    WebDriver driver;
    LoginPageFactory loginPage;
    HomePageFactory homePage;
    CheckoutPage checkoutPage;

    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Create all page objects — one per page
        loginPage = new LoginPageFactory(driver);
        homePage = new HomePageFactory(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // DataProvider for two test users
    @DataProvider(name = "users")
    public Object[][] users() {
        return new Object[][]{
                {"student", "Password123"},
                        // user1 - valid credentials
                {"student", "Password123"},
                // user 2 - one valid user
        };
    }

    // Test 1 — Full journey: Login → Home → Checkout
    @Test(
            dataProvider = "users",
            groups = {"smoke", "regression"},
            description = "Full user journey: login, verify home, go to checkout"
    )
    public void fullJourneytest(String username, String password){
        // Page 1: Login ──
        System.out.println("Page 1: Login");
        loginPage.navigateTo();
        loginPage.login(username, password);

        // Page 2: Home
        System.out.println("Page 2: Home");
        String heading = homePage.getHeadingText();
        System.out.println("Heading: " + heading);

        Assert.assertEquals(heading, "Logged In Successfully", "Login failed for the user: " + username);
        Assert.assertTrue(homePage.isLogoutVisible(), "Logout button should be visible!");

        // Page 3: Checkout/Exceptions page
        System.out.println("Page 3: Checkout");
        checkoutPage.navigateTo();

        String title = checkoutPage.getPageTitle();
        System.out.println("Title: " + title);
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty!");

        // Add a row and save
        checkoutPage.clickAdd();
        checkoutPage.typeInSecondRow("Test");
        checkoutPage.clickSave();

        String confirmation = checkoutPage.getConfirmMsg();
        System.out.println("Confirmation: " + confirmation);
        Assert.assertFalse(confirmation.isEmpty(), "Expected confirmation message!");

        System.out.println("Full customer journey completed for: " + username);
    }

    // Test 2 — Login then verify page elements
    @Test(
        groups = {"regression"},
        description = "Verify home page elements after login"
    )
    public void homePageElementsTest(){
        loginPage.navigateTo();
        loginPage.login("student","Password123");

        // Verify heading
        String heading = homePage.getHeadingText();
        Assert.assertEquals(heading, "Logged In Successfully");
        System.out.println("Heading verified: " + heading);

        //Verify url
        String url = homePage.getCurrentUrl();
        Assert.assertTrue(url.contains("logged-in-successfully"), "Expected logged-in URL!");
        System.out.println("URL verified: " + url);

        // Verify logout visible
        Assert.assertTrue(homePage.isLogoutVisible(), "Logout should be visible!");
        System.out.println("Logout button visible");
    }

    // Test 3 — Direct checkout page test
    @Test(
            groups = {"smoke", "regression"},
            description = "Verify checkout page add and save functionality"
    )
    public void checkoutPageTest(){
        checkoutPage.navigateTo();

        // Add new row
        checkoutPage.clickAdd();
        System.out.println("Add button clicked");

        //Type in second row
        checkoutPage.typeInSecondRow("Test1");
        System.out.println("Text entered in second row");

        // Save
        checkoutPage.clickSave();
        System.out.println("Save button worked");

        //verify Confirmation
        String msg = checkoutPage.getConfirmMsg();
        System.out.println("Confirmation msg: " + msg);
        Assert.assertFalse(msg.isEmpty(), "Expected confirmation after save!");
    }



 }

