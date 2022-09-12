package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getDriver;


public class LoginTest{

    HomePage HomeObject;
    LoginPage LoginObject;
    WebDriver driver;

    //need to be maintained it should start it from register
    @Test
    public void CheckLoginSuccessfully(){
        HomeObject.openLoginPage();
        LoginObject.Login("Mohamed@gmail.com","tester123");
        String Result = HomeObject.CheckLougoutLink();
        Assert.assertEquals(Result,"Log out");
    }

    @Test
    public void CheckLoginFaild(){
        HomeObject.openLoginPage();
        LoginObject.Login("ppp55@gmail.com","tester123");
        String Result = LoginObject.getLoginErrorMessage();
        Assert.assertEquals(Result,"Login was unsuccessful. Please correct the errors and try again.\n" +
                "No customer account found");
    }

    @BeforeMethod
    public void setUp(){
        driver = getDriver("chrome");

        //here we initialize all pageObject classes that we will be using
        HomeObject = new HomePage(driver);
        LoginObject = new LoginPage(driver);

        HomeObject.navigateToHomePage();
    }

    @AfterMethod
    public void closeDown(){
        closeAllBrowserTabs(driver);
    }
}
