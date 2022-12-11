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
    ShoppingCartPage CartObject;
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
    public void SearchForProductCompleteName() {
        String Product_name = JsonObject.getTestData("Product.CompleteProductName");
        SearchPageObject.searchForProductCompleteName(Product_name);
        SearchPageObject.openProjectDetailsPage(Product_name);
        Assert.assertEquals(ProductObject.getProductName(),Product_name);
    }

    @Test(dependsOnMethods = "SearchForProductCompleteName")
    public void AddProductToCart() {
        //Add product to cart
        String quantity = JsonObject.getTestData("Product.quantity");
        String Product_name = JsonObject.getTestData("Product.CompleteProductName");
        ProductObject.AddProductToCart(quantity);
        Assert.assertEquals(ProductObject.getSucessmsg(), JsonObject.getTestData("messages.AddToCartSuccessfully"));

        //Check if quantity and price are correct in the cart
        homeObject.openNotificationShoppingCart();
        Assert.assertEquals(CartObject.getProductQuantityInCart(Product_name),quantity);

        //Check if the total price is calculated correctly in shopping cart
        String ProductTotalPrice = CartObject.getProductTotalPrice(Product_name);
        Assert.assertEquals(ProductTotalPrice,CartObject.getProductCalculatedTotalPrice(Product_name));

        //check if the total price in table equal to total order price
        Assert.assertEquals(CartObject.getOrderTotalPrice(),ProductTotalPrice);
    }

    @Test(dependsOnMethods = "AddProductToCart")
    public void Checkout(){
        CartObject.NavigateToCheckoutPage();

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
        CartObject = new ShoppingCartPage(driver);

    }

    @AfterClass
    public void closeDown() {
        closeAllBrowserTabs(driver);
    }
}
