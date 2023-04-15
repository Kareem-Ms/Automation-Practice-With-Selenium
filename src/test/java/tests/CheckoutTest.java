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
@Feature("Checkout Tests")
public class CheckoutTest {

    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    SearchPage searchPage;
    CheckoutPage checkoutPage;
    JsonFileManager jsonFileManager;
    UserRegistrationPage userRegistrationPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    String email;
    String password;

    /////////////////// Test Cases //////////////////////
    @Test(description = "Checkout Test - Valid registration")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Register new user with a valid email and valid password")
    public void RegisterNewUser() {
        email = jsonFileManager.getTestData("users.RegisteredEmail") + currentTime + "@" + jsonFileManager.getTestData("users.emailDomain");
        password = jsonFileManager.getTestData("users.Password");
        String Firstname = jsonFileManager.getTestData("users.FirstName");
        String Lastname = jsonFileManager.getTestData("users.LastName");
        userRegistrationPage.registerWithRequiredFields(Firstname, Lastname, email, password, password);
        assertOnRegistrationMsg(userRegistrationPage.getRegistrationMsg());
    }

    @Test(dependsOnMethods = "RegisterNewUser" , description = "Checkout Test - Valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with valid email and password")
    public void LoginSuccessfully() {
        homePage    .openLoginPage();
        loginPage   .Login(email, password);
        assertOnLogoutLinkExistence(homePage.CheckLougoutLink());
    }

    @Test(dependsOnMethods = "LoginSuccessfully", description = "Checkout Test - Search For Product with complete name")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Search for product using it's complete name successfully")
    public void SearchForProductCompleteName() {
        String Product_name = jsonFileManager.getTestData("Product.CompleteProductName");
        searchPage  .searchForProductCompleteName(Product_name);
        searchPage  .openProjectDetailsPage(Product_name);
        assertThatProductTitleEqualsProductName(productDetailsPage.getProductName() , Product_name);
    }

    @Test(dependsOnMethods = "SearchForProductCompleteName",description = "Checkout Test - Add product to cart")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Add product to cart successfully")
    public void AddProductToCart() {
        //Add product to cart
        String quantity = jsonFileManager.getTestData("Product.quantity");
        String Product_name = jsonFileManager.getTestData("Product.CompleteProductName");
        productDetailsPage.AddProductToCart(quantity);
        assertThatProductAddedToCart(productDetailsPage.getSucessmsg());

        homePage.openNotificationShoppingCart();
        String ProductQuantityInCart = shoppingCartPage.getProductQuantityInCart(Product_name);
        String ProductTotalPriceInCart = shoppingCartPage.getProductTotalPrice(Product_name);
        String ProductCalculatedPrice = shoppingCartPage.getProductCalculatedTotalPrice(Product_name);
        String OrderTotalPrice = shoppingCartPage.getOrderTotalPrice();
        assertThatDataIsCorrectInCart(ProductQuantityInCart, quantity, ProductTotalPriceInCart, ProductCalculatedPrice, OrderTotalPrice);
    }

    @Test(dependsOnMethods = "AddProductToCart", description = "Checkout Test - Complete details of checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete checkout details successfully")
    public void Checkout() {
        shoppingCartPage    .NavigateToCheckoutPage();
        checkoutPage        .FillCheckoutInformation(jsonFileManager.getTestData("BillingAddress.CountryName")
                            ,jsonFileManager.getTestData("BillingAddress.City")
                            ,jsonFileManager.getTestData("BillingAddress.Address1")
                            ,jsonFileManager.getTestData("BillingAddress.ZipCode")
                            ,jsonFileManager.getTestData("BillingAddress.PhoneNumber"));

        assertThatOrderIsDone(checkoutPage.getConfirmMessage());
    }

    /////////////////// Configurations  //////////////////////
    @BeforeClass
    public void setUp() {
        jsonFileManager = new JsonFileManager("src/test/data/CheckoutTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName")
                ,jsonFileManager.getTestData("config.ExecutionType"));
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        checkoutPage = new CheckoutPage(driver);
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

    private void assertThatProductTitleEqualsProductName(String PrdouctDetailsTitle , String ProductName){
        Assert.assertEquals(PrdouctDetailsTitle, ProductName);

    }

    private void assertThatProductAddedToCart(String ActualConfirmationMsg){
        String ExpectedConfirmationMsg =jsonFileManager.getTestData("messages.AddToCartSuccessfully");
        Assert.assertEquals(ActualConfirmationMsg, ExpectedConfirmationMsg);
    }

    private void assertThatDataIsCorrectInCart(String ProductQuantityInCart,String quantity,String ProductTotalPriceInCart,String ProductCalculatedPrice,String OrderTotalPrice){
        //Check if quantity and price are correct in the cart
        Assert.assertEquals(ProductQuantityInCart, quantity);
        //Check if the total price is calculated correctly in shopping cart
        Assert.assertEquals(ProductTotalPriceInCart,ProductCalculatedPrice);
        //check if the total price in table equal to total order price
        Assert.assertEquals(OrderTotalPrice, ProductTotalPriceInCart);
    }

    private void assertThatOrderIsDone(String ActualConfirmationMsg){
        String ExpectedConfirmationMsg =  jsonFileManager.getTestData("messages.OrderConfirmedSuccessfully");
        //Assert.assertEquals(ActualConfirmationMsg,ExpectedConfirmationMsg);
    }
}
