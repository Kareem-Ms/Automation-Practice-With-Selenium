package pages;

import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class CheckoutPage {
    WebDriver driver;
    ElementActions actionObject;
    CheckoutBillingAddressPage BillingAddressObject;

    public CheckoutPage(WebDriver driver){
        this.driver = driver;
        actionObject = new ElementActions(driver);
        BillingAddressObject = new CheckoutBillingAddressPage(driver);
    }

    public void FillCheckoutInformation(){

    }

}
