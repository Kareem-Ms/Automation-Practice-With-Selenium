package tests;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import utils.JsonFileManager;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getBrowser;


@Epic("Regression tests")
@Feature("SearchProduct tests")
public class SearchProductTest {

    WebDriver driver;
    JsonFileManager jsonFileManager;
    HomePage homePage;
    SearchPage searchPage;
    ProductDetailsPage productDetailsPage;

    /////////////////// Test Cases //////////////////////
    @Test(description = "SearchProduct Tests - Search for product with complete name")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Search for product using it's full name successfully")
    public void SearchForProduct() {
        String Product_name = jsonFileManager.getTestData("FullProductName");
        searchPage  .searchForProductCompleteName(Product_name);
        assertThatProductTitleEqualsProductName(productDetailsPage.getProductTitle(), Product_name);
    }

    @Test(description = "SearchProduct Tests - Search for product with part of it's name")
    @Severity(SeverityLevel.NORMAL)
    @Description("Search for product using part of it's name successfully")
    public void SearchForProductAutoComplete() {
        String AutoCompleteProductName = jsonFileManager.getTestData("AutoCompleteProductName");
        searchPage  .searchProductAutoComplete(AutoCompleteProductName, 0);
        assertThatProductNameContainAutoCompleteName(productDetailsPage.getProductName(),AutoCompleteProductName);
    }

    /////////////////// Configurations  //////////////////////
    @BeforeMethod
    public void setUp() {
        jsonFileManager = new JsonFileManager("src/test/data/SearchProductTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"), jsonFileManager.getTestData("config.ExecutionType"));
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        homePage.navigateToHomePage();
    }

    @AfterMethod
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }

    /////////////////// Assertions //////////////////////
    private void assertThatProductTitleEqualsProductName(String PrdouctDetailsTitle , String ProductName){
        Assert.assertEquals(PrdouctDetailsTitle, ProductName);
    }

    private void assertThatProductNameContainAutoCompleteName(String ProductName , String AutoCompleteName){
        Assert.assertTrue(ProductName.contains(AutoCompleteName));
    }
}
