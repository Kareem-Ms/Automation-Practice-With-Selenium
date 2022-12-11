package pages;

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


    public String getProductName(){
        return actionObject.getText(ProductNameLocator);
    }

    public String getProductTitle(){
        return actionObject.getText(ProductTitleLocator);
    }

    public void AddProductToCart(String quantity){
        actionObject.type(ProductQuantityTextBoxLocator, quantity);
        actionObject.click(AddToCartLocator);
    }

    public String getSucessmsg(){
        return actionObject.getText(SucessMessage);
    }
}
