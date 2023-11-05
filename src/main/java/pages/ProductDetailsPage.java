package pages;

import io.qameta.allure.Step;
import utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage {

    WebDriver driver;
    ElementActions actionObject;

    public ProductDetailsPage(WebDriver driver){
        this.driver = driver;
        actionObject = new ElementActions(driver);
    }

    By ProductNameLocator = By.className("product-name");
    By ProductTitleLocator = By.className("product-title");
    By ProductQuantityTextBoxLocator = By.xpath("//div//input[@class = 'qty-input']");
    By AddToCartLocator = By.className("add-to-cart-button");
    By SucessMessage = By.cssSelector("p.content");


    @Step("Get product name from product details page")
    public String getProductName(){
        return actionObject.getText(ProductNameLocator);
    }

    @Step("Get product title from product details page")
    public String getProductTitle(){
        return actionObject.getText(ProductTitleLocator);
    }

    @Step("Add prdouct to cart from product details page with quantity: [0]")
    public void AddProductToCart(String quantity){
        actionObject.type(ProductQuantityTextBoxLocator, quantity);
        actionObject.click(AddToCartLocator);
    }

    @Step("Get added to cart alert message")
    public String getSucessmsg(){
        return actionObject.getText(SucessMessage);
    }
}
