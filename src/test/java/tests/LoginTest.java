package tests;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
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
    WebDriver driver;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    String email;
    String password;

    /////////////////// Test Cases //////////////////////
    @Test(description = "Login Tests - Valid Registration")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Register new user with a valid email and valid password")
    public void RegisterNewUser() {
        email = jsonFileManager.getTestData("users.RegisteredEmail") + currentTime + "@" + jsonFileManager.getTestData("users.emailDomain");
        password = jsonFileManager.getTestData("users.Password");
        String Firstname = jsonFileManager.getTestData("users.FirstName");
        String Lastname = jsonFileManager.getTestData("users.LastName");
        userRegistrationPage    .registerWithRequiredFields(Firstname, Lastname, email, password, password);
        assertOnRegistrationMsg(userRegistrationPage.getRegistrationMsg());
    }

    @Test(dependsOnMethods = "RegisterNewUser", description = "Login Tests - Valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with valid email and password")
    public void CheckLoginSuccessfully() {
        homePage    .openLoginPage();
        loginPage   .Login(email, password);
        assertOnLogoutLinkExistence(homePage.CheckLougoutLink());
    }

    @Test(description = "Login Tests - Invalid login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with invalid email and password")
    public void CheckLoginFaild() {
        String UnregisterEmail = jsonFileManager.getTestData("users.UnRegisteredEmail") + "@" + jsonFileManager.getTestData("users.emailDomain");
        homePage    .openLoginPage();
        loginPage   .Login(UnregisterEmail, jsonFileManager.getTestData("users.Password"));
        assertOnLoginFaildMsg(loginPage.getLoginErrorMessage());
    }

    /////////////////// Configurations  //////////////////////
    @BeforeMethod
    public void setUp(){
        jsonFileManager = new JsonFileManager("src/test/data/LoginTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"), jsonFileManager.getTestData("config.ExecutionType"));
        //here we initialize all pageObject classes that we will be using
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        userRegistrationPage.navigateToRegisterPage();
    }

    @AfterMethod
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }

    /////////////////// Assertions //////////////////////
    private void assertOnRegistrationMsg(String ActualRegistrationMsg){
        String expectedRegistrationMsg = jsonFileManager.getTestData("messages.RegisterSuccessfully");
        Assert.assertEquals(ActualRegistrationMsg, expectedRegistrationMsg);
    }

    private void assertOnLogoutLinkExistence(String LogoutLinkText){
       String ExpectedLogoutText = jsonFileManager.getTestData("messages.LoginSuccessfully");
       Assert.assertEquals(ExpectedLogoutText,LogoutLinkText);
    }

    private void assertOnLoginFaildMsg(String ActualLoginFaildMsg){
        String ExpectedLoginFaildMsg = jsonFileManager.getTestData("messages.LoginFail");
        Assert.assertEquals(ActualLoginFaildMsg,ExpectedLoginFaildMsg);
    }

}