package tests;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.UserRegisterResultPage;
import pages.UserRegistrationPage;
import utils.JsonFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getBrowser;


@Epic("Regression tests")
@Feature("Login tests")
public class LoginTest {

    JsonFileManager jsonFileManager;
    HomePage homePage;
    UserRegistrationPage userRegistrationPage;
    LoginPage loginPage;
    UserRegisterResultPage userRegisterResultPage;
    WebDriver driver;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    String email;
    String password;

    @Test(description = "Login Tests - Valid Registration")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Register new user with a valid email and valid password")
    public void RegisterNewUser() {

        email = jsonFileManager.getTestData("users.RegisteredEmail")
                + currentTime + "@" + jsonFileManager.getTestData("users.emailDomain");
        password = jsonFileManager.getTestData("users.Password");

        userRegistrationPage.registerWithRequiredFields(jsonFileManager.getTestData("users.FirstName"),
                jsonFileManager.getTestData("users.LastName"), email, password, password);
        //we will check that the user is registered
        String msg = userRegisterResultPage.checkmsg();
        Assert.assertEquals(msg, jsonFileManager.getTestData("messages.RegisterSuccessfully"));
    }

    @Test(dependsOnMethods = "RegisterNewUser", description = "Login Tests - Valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with valid email and password")
    public void CheckLoginSuccessfully() {
        homePage.openLoginPage();
        loginPage.Login(email, password);
        String Result = homePage.CheckLougoutLink();
        Assert.assertEquals(Result, jsonFileManager.getTestData("messages.LoginSuccessfully"));
    }

    @Test(description = "Login Tests - Invalid login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with invalid email and password")
    public void CheckLoginFaild() {
        homePage.openLoginPage();
        String UnregisterEmail = jsonFileManager.getTestData("users.UnRegisteredEmail") + "@"
                + jsonFileManager.getTestData("users.emailDomain");
        loginPage.Login(UnregisterEmail, jsonFileManager.getTestData("users.Password"));
        String Result = loginPage.getLoginErrorMessage();
        Assert.assertEquals(Result, jsonFileManager.getTestData("messages.LoginFail"));

    }

    @BeforeMethod
    public void setUp(){
        jsonFileManager = new JsonFileManager("src/test/data/LoginTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"),
                jsonFileManager.getTestData("config.ExecutionType"));
        //here we initialize all pageObject classes that we will be using
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        userRegisterResultPage = new UserRegisterResultPage(driver);
        userRegistrationPage.navigateToRegisterPage();
    }

    @AfterMethod
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }
}
