package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class ShoppingCartPage {
    WebDriver driver;
    ElementActions actionObject;

    public ShoppingCartPage (WebDriver driver){
        this.driver = driver;
        actionObject = new ElementActions(driver);
    }

    //need to understand how to write a relative xpath so that i could get into the cell of any row in the table without using findbyelemts
    String TotalOrderXpath = "//td[@class = 'cart-total-left']//label[text() = 'Total:']//parent::td//following-sibling::td//span//strong";
    By TotalOrderLocator = By.xpath(TotalOrderXpath);
    By TermsAndConditionInputLocator = By.id("termsofservice");
    By CheckOutButtonLocator = By.id("checkout");

    public String getProductQuantityInCart(String ProductName){
       return actionObject.getAccessibleName(getProductQunatityXpath(ProductName));
    }

    public String getProductTotalPrice(String ProductName){
        String TotalPriceWithoutSign = actionObject.getText(getTotalPriceXpath(ProductName)).substring(1);
        TotalPriceWithoutSign = TotalPriceWithoutSign.replaceAll(",", "");
        int totalPrice = (int)Math.round(Double.parseDouble(TotalPriceWithoutSign));
        return String.valueOf(totalPrice);
    }

    public String getProductCalculatedTotalPrice(String ProductName){
        int quantity = Integer.parseInt(actionObject.getAccessibleName(getProductQunatityXpath(ProductName)));
        //remove $ sign from unit price and form it into integer
        String unitPriceWithoutSign = actionObject.getText(getProductUnitPriceXpath(ProductName)).substring(1);
        //here we replace all , with a space to convert it into integer
        unitPriceWithoutSign = unitPriceWithoutSign.replaceAll(",", "");
        int unitPrice = (int)Math.round(Double.parseDouble(unitPriceWithoutSign));
        int CalculatedTotal = quantity*unitPrice;
        return String.valueOf(CalculatedTotal);
    }

    public String getOrderTotalPrice(){
        String TotalOrderPriceWithoutSign = actionObject.getText(TotalOrderLocator).substring(1);
        TotalOrderPriceWithoutSign = TotalOrderPriceWithoutSign.replaceAll(",", "");
        int OrderTotalPrice = (int)Math.round(Double.parseDouble(TotalOrderPriceWithoutSign));
        return String.valueOf(OrderTotalPrice);
    }

    public void NavigateToCheckoutPage(){
        actionObject.click(TermsAndConditionInputLocator);
        actionObject.click(CheckOutButtonLocator);
    }

    private By getTotalPriceXpath(String ProductName){
        By Locator = By.xpath("//td//a[text() = '"+ProductName+"']//parent::td[@class = 'product']//following-sibling::td[@class = 'subtotal']//span[@class = 'product-subtotal']");
        return Locator;
    }

    private By getProductQunatityXpath(String ProductName){
        By Locator = By.xpath("//td//a[text() = '"+ProductName+"']//parent::td[@class = 'product'] //following-sibling::td[@class = 'quantity']");
        return Locator;
    }

    private By getProductUnitPriceXpath(String ProductName){
        By Locator = By.xpath("//td//a[text() = '"+ProductName+"']//parent::td[@class = 'product']//following-sibling::td[@class = 'unit-price']//span[@class = 'product-unit-price']");
        return Locator;
    }
}
