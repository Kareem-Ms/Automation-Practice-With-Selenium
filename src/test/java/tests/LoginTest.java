package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.UserRegistrationPage;
import utils.BrowserFactory;
import utils.JsonFileManager;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getBrowser;



public class LoginTest {

    JsonFileManager jsonFileManager;
    HomePage homePage;
    UserRegistrationPage userRegistrationPage;
    LoginPage loginPage;
    WebDriver driver;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());

    //need to be maintained it should start it from register
    @Test
    public void CheckLoginSuccessfully() {
        homePage.openRegistrationPage();
        String email = jsonFileManager.getTestData("users.RegisteredEmail")
                + currentTime + "@" + jsonFileManager.getTestData("users.emailDomain");
        String password = jsonFileManager.getTestData("users.Password");


        //register using data
        userRegistrationPage.registerWithRequiredFields(jsonFileManager.getTestData("users.FirstName"),
                jsonFileManager.getTestData("users.LastName"), email, password, password);

        //Login
        homePage.openLoginPage();
        loginPage.Login(email, password);
        String Result = homePage.CheckLougoutLink();
        Assert.assertEquals(Result, jsonFileManager.getTestData("messages.LoginSuccessfully"));
    }

    @Test
    public void CheckLoginFaild() {
        homePage.openLoginPage();
        String UnregisterEmail = jsonFileManager.getTestData("users.UnRegisteredEmail") + "@"
                + jsonFileManager.getTestData("users.emailDomain");
        loginPage.Login(UnregisterEmail, jsonFileManager.getTestData("users.Password"));
        String Result = loginPage.getLoginErrorMessage();
        Assert.assertEquals(Result, jsonFileManager.getTestData("messages.LoginFail"));

    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        jsonFileManager = new JsonFileManager("src/test/data/LoginTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"),
                jsonFileManager.getTestData("config.ExecutionType"));
        //here we initialize all pageObject classes that we will be using
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        homePage.navigateToHomePage();
    }

    @AfterMethod
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }
}
