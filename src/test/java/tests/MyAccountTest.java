package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CustomerInfoPage;
import pages.HomePage;
import pages.LoginPage;
import pages.UserRegistrationPage;
import utils.JsonFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getDriver;

public class MyAccountTest {

    HomePage HomePageObject;
    LoginPage LoginPageObject;
    UserRegistrationPage UserRegisterObject;
    CustomerInfoPage CustomerPageObject;
    WebDriver driver;
    JsonFileManager JsonObject;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());


    @Test
    public void ChangePasswordSuccessfully(){

        HomePageObject = new HomePage(driver);
        HomePageObject.openRegistrationPage();
        String email = JsonObject.getTestData("users.Email")+currentTime+"@"+JsonObject.getTestData("users.emailDomain");
        String OldPassword = JsonObject.getTestData("users.OldPassword");
        String NewPassword = JsonObject.getTestData("users.NewPassword");
        UserRegisterObject.registerWithRequiredFields(JsonObject.getTestData("users.firstname"),
                JsonObject.getTestData("users.LastName"),email,OldPassword,OldPassword);

        HomePageObject.openMyAccount();
        CustomerPageObject.openChangePassword();
        CustomerPageObject.ChangePassword(OldPassword,NewPassword,NewPassword);
        String message = CustomerPageObject.getConfirmationMessage();
        if(message!=null){
            Assert.assertEquals(message,JsonObject.getTestData("messages.PasswordChanged"));
        }
        else{
            Assert.fail();
        }
    }

    @BeforeMethod
    public void setup(){
        JsonObject = new JsonFileManager("src/test/data/MyAccountTestData.json");
        driver = getDriver("chrome");
        HomePageObject = new HomePage(driver);
        UserRegisterObject = new UserRegistrationPage(driver);
        LoginPageObject = new LoginPage(driver);
        CustomerPageObject = new CustomerInfoPage(driver);
        HomePageObject.navigateToHomePage();
    }

    @AfterMethod
    public void closeDown(){
        closeAllBrowserTabs(driver);
    }
}
