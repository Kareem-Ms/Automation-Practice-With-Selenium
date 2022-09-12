package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CustomerInfoPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getDriver;

public class MyAccountTest {

    HomePage HomePageObject;
    LoginPage LoginPageObject;
    CustomerInfoPage CustomerPageObject;
    WebDriver driver;

    @Test
    public void ChangePasswordSuccessfully(){
        HomePageObject = new HomePage(driver);
        HomePageObject.openLoginPage();
        String NewPassword = "tester1234";
        String email = "Mohamed@gmail.com";
        String OldPassword = "tester123";
        LoginPageObject.Login(email,OldPassword);
        HomePageObject.openMyAccount();
        CustomerPageObject.openChangePassword();
        CustomerPageObject.ChangePassword(OldPassword,NewPassword,NewPassword);
        String message = CustomerPageObject.getConfirmationMessage();
        if(message!=null){
            Assert.assertEquals(message,"Password was changed");
        }
        else{
            Assert.fail();
        }
    }

    @BeforeMethod
    public void setup(){
        driver = getDriver("chrome");
        HomePageObject = new HomePage(driver);
        LoginPageObject = new LoginPage(driver);
        CustomerPageObject = new CustomerInfoPage(driver);
        HomePageObject.navigateToHomePage();
    }

    @AfterMethod
    public void closeDown(){
        closeAllBrowserTabs(driver);
    }
}
