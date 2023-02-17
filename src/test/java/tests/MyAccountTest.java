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
    UserRegisterResultPage userRegisterResultPage;
    WebDriver driver;
    JsonFileManager jsonFileManager;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    String email;
    String password;


    @Test(description = "MyAccount Tests - Valid Registration")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Register new user with a valid email and valid password")
    public void RegisterNewUser() {
         email = jsonFileManager.getTestData("users.Email") + currentTime + "@"
                + jsonFileManager.getTestData("users.emailDomain");
        password = jsonFileManager.getTestData("users.OldPassword");

        //b3d keda ha3rf el Register object
        userRegistrationPage.registerWithRequiredFields(jsonFileManager.getTestData("users.firstname"),
                jsonFileManager.getTestData("users.LastName"), email, password, password);
        //we will check that the user is registered
        String msg = userRegisterResultPage.checkmsg();
        Assert.assertEquals(msg, jsonFileManager.getTestData("messages.RegisterSuccessfully"));
    }

    @Test(dependsOnMethods = "RegisterNewUser", description = "MyAccount Tests - Valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with valid email and password")
    public void Login(){
        homePage.openLoginPage();
        loginPage.Login(email, password);
        String Result = homePage.CheckLougoutLink();
        Assert.assertEquals(Result, jsonFileManager.getTestData("messages.LoginSuccessfully"));
    }

    @Test(dependsOnMethods = "Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Change password successfully")
    public void ChangePassword() {
        //Change password to the new password
        homePage.openMyAccount();
        customerInfoPage.openChangePassword();
        String NewPassword = jsonFileManager.getTestData("users.NewPassword");
        customerInfoPage.ChangePassword(password, NewPassword, NewPassword);
        String message = customerInfoPage.getConfirmationMessage();

        //check if the password is changed or not
        Assert.assertEquals(message, jsonFileManager.getTestData("messages.PasswordChanged"));
    }

    @BeforeClass
    public void setup() {
        jsonFileManager = new JsonFileManager("src/test/data/MyAccountTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"),
                jsonFileManager.getTestData("config.ExecutionType"));
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        customerInfoPage = new CustomerInfoPage(driver);
        userRegisterResultPage = new UserRegisterResultPage(driver);
        userRegistrationPage.navigateToRegisterPage();
    }

    @AfterClass
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }
}
