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

    By TotalOrderLocator = By.xpath("//td[@class = 'cart-total-left']//label[text() = 'Total:']//parent::td//following-sibling::td//span//strong");
    By TermsAndConditionInputLocator = By.id("termsofservice");
    By CheckOutButtonLocator = By.id("checkout");

    public String getProductQuantityInCart(String ProductName){
       String ProductQuantityXpath = "//td//a[text() = '"+ProductName+"']//parent::td[@class = 'product'] //following-sibling::td[@class = 'quantity']";
       return actionObject.getAccessibleName(By.xpath(ProductQuantityXpath));
    }

    public String getProductTotalPrice(String ProductName){
        String ProductTotalPriceXpath = "//td//a[text() = '"+ProductName+"']//parent::td[@class = 'product']//following-sibling::td[@class = 'subtotal']//span[@class = 'product-subtotal']";
        //change the format of total price into an ordinary integer
        String TotalPriceWithoutSign = actionObject.getText(By.xpath(ProductTotalPriceXpath)).substring(1);
        TotalPriceWithoutSign = TotalPriceWithoutSign.replaceAll(",", "");
        int totalPrice = (int)Math.round(Double.parseDouble(TotalPriceWithoutSign));
        return String.valueOf(totalPrice);
    }

    public String getProductCalculatedTotalPrice(String ProductName){
        String ProductQuantityXpath = "//td//a[text() = '"+ProductName+"']//parent::td[@class = 'product'] //following-sibling::td[@class = 'quantity']";
        String ProductUnitPriceXpath = "//td//a[text() = '"+ProductName+"']//parent::td[@class = 'product']//following-sibling::td[@class = 'unit-price']//span[@class = 'product-unit-price']";
        int quantity = Integer.parseInt(actionObject.getAccessibleName(By.xpath(ProductQuantityXpath)));
        //remove $ sign from unit price and form it into integer
        String unitPriceWithoutSign = actionObject.getText(By.xpath(ProductUnitPriceXpath)).substring(1);
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
}
