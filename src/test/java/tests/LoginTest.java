package tests;

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
import static utils.BrowserFactory.getDriver;


public class LoginTest{

    JsonFileManager JsonObject;
    HomePage HomeObject;
    UserRegistrationPage UserRegisterObject;
    LoginPage LoginObject;
    WebDriver driver;

    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());

    //need to be maintained it should start it from register
    @Test
    public void CheckLoginSuccessfully(){
        HomeObject.openLoginPage();
        String email = JsonObject.getTestData("users.RegisteredEmail")+"@"+JsonObject.getTestData("users.emailDomain");
        String password = JsonObject.getTestData("users.Password");

        LoginObject.Login(email,password);
        String Result = HomeObject.CheckLougoutLink();
        Assert.assertEquals(Result, JsonObject.getTestData("messages.LoginSuccessfully"));
    }

    @Test
    public void CheckLoginFaild(){
        HomeObject.openLoginPage();

        LoginObject.Login(JsonObject.getTestData("users.UnRegisteredEmail"),JsonObject.getTestData("users.Password"));
        String Result = LoginObject.getLoginErrorMessage();
        Assert.assertEquals(Result,JsonObject.getTestData("messages.LoginFail"));

    }

    @BeforeMethod
    public void setUp(){
        JsonObject = new JsonFileManager("src/test/data/LoginTestData.json");
        driver = getDriver("chrome");

        //here we initialize all pageObject classes that we will be using
        HomeObject = new HomePage(driver);
        UserRegisterObject = new UserRegistrationPage(driver);
        LoginObject = new LoginPage(driver);

        HomeObject.navigateToHomePage();
    }

    @AfterMethod
    public void closeDown(){
        closeAllBrowserTabs(driver);
    }
}
