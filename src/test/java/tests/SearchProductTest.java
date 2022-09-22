package tests;

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
import static utils.BrowserFactory.getDriver;

public class SearchProductTest {

    WebDriver driver;
    JsonFileManager JsonObject;

    HomePage homePageObject;
    SearchPage SearchPageObject;
    ProductDetailsPage ProductObject;

    @Test
    public void SearchForProduct(){
        String Product_name = JsonObject.getTestData("FullProductName");
        SearchPageObject.searchForProductCompleteName(Product_name);
        Assert.assertEquals(ProductObject.getProductTitle(),Product_name);
    }

    @Test
    public void SearchForProductAutoComplete(){
        String Product_name = JsonObject.getTestData("AutoCompleteProductName");
        SearchPageObject.searchProductAutoComplete(Product_name,0);
        Assert.assertTrue(ProductObject.getProductName().contains(Product_name));
    }

    @BeforeMethod
    public void startUp(){
        JsonObject = new JsonFileManager("src/test/data/SearchProductTestData.json");
        driver = getDriver("chrome");
        homePageObject = new HomePage(driver);
        SearchPageObject = new SearchPage(driver);
        ProductObject = new ProductDetailsPage(driver);

        homePageObject.navigateToHomePage();
    }

    @AfterMethod
    public void closeUp(){
        closeAllBrowserTabs(driver);
    }
}
