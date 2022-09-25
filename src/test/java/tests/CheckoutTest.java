package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;
import utils.JsonFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getDriver;

public class CheckoutTest {

    WebDriver driver;
    JsonFileManager JsonObject;

   // 1-Register a new user
   // 2-Search for specific product
   // 3-Add product to cart
   // 4-Go to shopping cart
   // 5-Accept the terms and
   // 6-enter billing information
   // 7-Click on continue
   // 8-

    HomePage homeObject;
    UserRegistrationPage RegisterObject;
    UserRegisterResultPage ResultObject;
    SearchPage SearchPageObject;
    ProductDetailsPage ProductObject;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());

    @Test
    public void UserRegister(){
        homeObject.openRegistrationPage();
        String email = JsonObject.getTestData("users.Email")+currentTime+"@"+JsonObject.getTestData("users.emailDomain");
        String password = JsonObject.getTestData("users.Password");

        RegisterObject.registerWithRequiredFields(JsonObject.getTestData("users.firstname"),
                JsonObject.getTestData("users.LastName"),email,password,password);

        String msg = ResultObject.checkmsg();
        Assert.assertEquals(msg, JsonObject.getTestData("messages.RegisterSuccessfully"));
    }

    @Test(dependsOnMethods = "UserRegister")
    public void SearchForProduct(){
        String Product_name = JsonObject.getTestData("FullProductName");
        SearchPageObject.searchForProductCompleteName(Product_name);
        Assert.assertEquals(ProductObject.getProductTitle(),Product_name);
    }


    @BeforeClass
    public void setUp(){
        JsonObject = new JsonFileManager("");
        driver = getDriver("chrome");

        homeObject = new HomePage(driver);
        RegisterObject = new UserRegistrationPage(driver);
        ResultObject = new UserRegisterResultPage(driver);
        SearchPageObject = new SearchPage(driver);
        ProductObject = new ProductDetailsPage(driver);
    }

    @AfterClass
    public void closeDown(){
        closeAllBrowserTabs(driver);
    }
}
