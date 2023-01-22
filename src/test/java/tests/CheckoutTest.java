package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;
import utils.BrowserFactory;
import utils.JsonFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getBrowser;


public class CheckoutTest {

    WebDriver driver;
    JsonFileManager jsonFileManager;
    HomePage homePage;
    UserRegistrationPage userRegistrationPage;
    LoginPage loginPage;
    UserRegisterResultPage userRegisterResultPage;
    SearchPage searchPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    String email;
    String password;

    @Test
    public void RegisterNewUser() {
        homePage.openRegistrationPage();
        email = jsonFileManager.getTestData("users.RegisteredEmail") + currentTime
                + "@" + jsonFileManager.getTestData("users.emailDomain");
        password = jsonFileManager.getTestData("users.Password");

        userRegistrationPage.registerWithRequiredFields(jsonFileManager.getTestData("users.FirstName"),
                jsonFileManager.getTestData("users.LastName"), email, password, password);

        String msg = userRegisterResultPage.checkmsg();
        Assert.assertEquals(msg, jsonFileManager.getTestData("messages.RegisterSuccessfully"));
    }

    @Test(dependsOnMethods = "RegisterNewUser")
    public void LoginSuccessfully() {
        homePage.openLoginPage();
        loginPage.Login(email, password);
        String LogoutWord = homePage.CheckLougoutLink();
        Assert.assertEquals(LogoutWord, jsonFileManager.getTestData("messages.LoginSuccessfully"));
    }

    @Test(dependsOnMethods = "LoginSuccessfully")
    public void SearchForProductCompleteName() {
        String Product_name = jsonFileManager.getTestData("Product.CompleteProductName");
        searchPage.searchForProductCompleteName(Product_name);
        searchPage.openProjectDetailsPage(Product_name);
        Assert.assertEquals(productDetailsPage.getProductName(), Product_name);
    }

    @Test(dependsOnMethods = "SearchForProductCompleteName")
    public void AddProductToCart() {
        //Add product to cart
        String quantity = jsonFileManager.getTestData("Product.quantity");
        String Product_name = jsonFileManager.getTestData("Product.CompleteProductName");
        productDetailsPage.AddProductToCart(quantity);
        Assert.assertEquals(productDetailsPage.getSucessmsg(), jsonFileManager.getTestData("messages.AddToCartSuccessfully"));

        //Check if quantity and price are correct in the cart
        homePage.openNotificationShoppingCart();
        Assert.assertEquals(shoppingCartPage.getProductQuantityInCart(Product_name), quantity);

        //Check if the total price is calculated correctly in shopping cart
        String ProductTotalPrice = shoppingCartPage.getProductTotalPrice(Product_name);
        Assert.assertEquals(ProductTotalPrice, shoppingCartPage.getProductCalculatedTotalPrice(Product_name));

        //check if the total price in table equal to total order price
        Assert.assertEquals(shoppingCartPage.getOrderTotalPrice(), ProductTotalPrice);
    }

    @Test(dependsOnMethods = "AddProductToCart")
    public void Checkout() {
        shoppingCartPage.NavigateToCheckoutPage();
        checkoutPage.FillCheckoutInformation(jsonFileManager.getTestData("BillingAddress.CountryName"),
                jsonFileManager.getTestData("BillingAddress.City"),
                jsonFileManager.getTestData("BillingAddress.Address1"),
                jsonFileManager.getTestData("BillingAddress.ZipCode"),
                jsonFileManager.getTestData("BillingAddress.PhoneNumber"));

        //Check if the order is done successfully
        Assert.assertEquals(checkoutPage.getConfirmMessage(), jsonFileManager.getTestData("messages.OrderConfirmedSuccessfully"));


    }


    @BeforeClass
    public void setUp() {
        jsonFileManager = new JsonFileManager("src/test/data/CheckoutTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"),
                jsonFileManager.getTestData("config.ExecutionType"));
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        userRegisterResultPage = new UserRegisterResultPage(driver);
        searchPage = new SearchPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        checkoutPage = new CheckoutPage(driver);

    }

    @AfterClass
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }
}
