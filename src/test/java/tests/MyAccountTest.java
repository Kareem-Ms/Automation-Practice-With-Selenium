package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CustomerInfoPage;
import pages.HomePage;
import pages.LoginPage;
import pages.UserRegistrationPage;
import utils.BrowserFactory;
import utils.JsonFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getBrowser;


public class MyAccountTest {

    HomePage homePage;
    UserRegistrationPage userRegistrationPage;
    LoginPage loginPage;
    CustomerInfoPage customerInfoPage;
    WebDriver driver;
    JsonFileManager jsonFileManager;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());


    @Test
    public void ChangePasswordSuccessfully() {

        //open register page and prepare registration data
        homePage.openRegistrationPage();
        String email = jsonFileManager.getTestData("users.Email") + currentTime
                + "@" + jsonFileManager.getTestData("users.emailDomain");
        String OldPassword = jsonFileManager.getTestData("users.OldPassword");
        String NewPassword = jsonFileManager.getTestData("users.NewPassword");

        //Register with data
        userRegistrationPage.registerWithRequiredFields(jsonFileManager.getTestData("users.firstname"),
                jsonFileManager.getTestData("users.LastName"), email, OldPassword, OldPassword);

        //Login to the registered account
        homePage.openLoginPage();
        loginPage.Login(email, OldPassword);

        //Change password to the new password
        homePage.openMyAccount();
        customerInfoPage.openChangePassword();
        customerInfoPage.ChangePassword(OldPassword, NewPassword, NewPassword);
        String message = customerInfoPage.getConfirmationMessage();

        //check if the password is changed or not
        Assert.assertEquals(message, jsonFileManager.getTestData("messages.PasswordChanged"));

    }

    @BeforeMethod
    public void setup() {
        jsonFileManager = new JsonFileManager("src/test/data/MyAccountTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"),
                jsonFileManager.getTestData("config.ExecutionType"));
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        customerInfoPage = new CustomerInfoPage(driver);
        homePage.navigateToHomePage();
    }

    @AfterMethod
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }
}
