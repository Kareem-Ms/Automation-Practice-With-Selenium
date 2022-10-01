package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

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
    public void RegisterNewUser() {
        homeObject.openRegistrationPage();
        String email = JsonObject.getTestData("users.RegisteredEmail") + currentTime + "@" + JsonObject.getTestData("users.emailDomain");
        String password = JsonObject.getTestData("users.Password");

        RegisterObject.registerWithRequiredFields(JsonObject.getTestData("users.FirstName"),
                JsonObject.getTestData("users.LastName"), email, password, password);

        String msg = ResultObject.checkmsg();
        Assert.assertEquals(msg, JsonObject.getTestData("messages.RegisterSuccessfully"));
    }

    @Test(dependsOnMethods = "RegisterNewUser")
    public void SearchForProductAutoComplete() {
        String Product_name = JsonObject.getTestData("Product.AutoCompleteProductName");
        SearchPageObject.searchProductAutoComplete(Product_name, 0);
        Assert.assertTrue(ProductObject.getProductName().contains(Product_name));
    }

    @Test(dependsOnMethods = "SearchForProductAutoComplete")
    public void AddProductToCart() {
        //Add product to cart
        String quantity = JsonObject.getTestData("Product.quantity");
        ProductObject.AddProductToCart(quantity);
        Assert.assertEquals(ProductObject.getSucessmsg(), JsonObject.getTestData("messages.AddToCartSuccessfully"));

        //Check if quantity and price are correct in the cart
        homeObject.openShoppingCart();

    }


    @BeforeClass
    public void setUp() {
        JsonObject = new JsonFileManager("src/test/data/CheckoutTestData.json");
        driver = getDriver("chrome");

        homeObject = new HomePage(driver);
        homeObject.navigateToHomePage();
        RegisterObject = new UserRegistrationPage(driver);
        ResultObject = new UserRegisterResultPage(driver);
        SearchPageObject = new SearchPage(driver);
        ProductObject = new ProductDetailsPage(driver);
    }

    @AfterClass
    public void closeDown() {
        closeAllBrowserTabs(driver);
    }
}
