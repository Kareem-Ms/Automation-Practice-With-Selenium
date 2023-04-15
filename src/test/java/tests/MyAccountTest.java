package tests;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.JsonFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getBrowser;

@Epic("Regression tests")
@Feature("MyAccount tests")
public class MyAccountTest {

    HomePage homePage;
    UserRegistrationPage userRegistrationPage;
    LoginPage loginPage;
    CustomerInfoPage customerInfoPage;
    WebDriver driver;
    JsonFileManager jsonFileManager;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    String email;
    String password;

    /////////////////// Test Cases //////////////////////
    @Test(description = "MyAccount Tests - Valid Registration")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Register new user with a valid email and valid password")
    public void RegisterNewUser() {
        email = jsonFileManager.getTestData("users.RegisteredEmail") + currentTime + "@" + jsonFileManager.getTestData("users.emailDomain");
        password = jsonFileManager.getTestData("users.OldPassword");
        String Firstname = jsonFileManager.getTestData("users.FirstName");
        String Lastname = jsonFileManager.getTestData("users.LastName");
        userRegistrationPage    .registerWithRequiredFields(Firstname, Lastname, email, password, password);
        assertOnRegistrationMsg(userRegistrationPage.getRegistrationMsg());
    }

    @Test(dependsOnMethods = "RegisterNewUser", description = "MyAccount Tests - Valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with valid email and password")
    public void Login(){
        homePage    .openLoginPage();
        loginPage   .Login(email, password);
        assertOnLogoutLinkExistence(homePage.CheckLougoutLink());
    }

    @Test(dependsOnMethods = "Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Change password successfully")
    public void ChangePassword() {
        String NewPassword = jsonFileManager.getTestData("users.NewPassword");
        homePage            .openMyAccount();
        customerInfoPage    .openChangePassword();
        customerInfoPage    .ChangePassword(password, NewPassword, NewPassword);
        assertThatPasswordChangedSuccessfully(customerInfoPage.getConfirmationMessage());
    }

    /////////////////// Configurations  //////////////////////
    @BeforeClass
    public void setup() {
        jsonFileManager = new JsonFileManager("src/test/data/MyAccountTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"), jsonFileManager.getTestData("config.ExecutionType"));
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        customerInfoPage = new CustomerInfoPage(driver);
        userRegistrationPage.navigateToRegisterPage();
    }

    @AfterClass
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

    private void assertThatPasswordChangedSuccessfully(String ActualPasswordChangedMsg){
        String ExpectedPasswordChangedMsg = jsonFileManager.getTestData("messages.PasswordChanged");
        Assert.assertEquals(ActualPasswordChangedMsg,ExpectedPasswordChangedMsg );
    }
}
