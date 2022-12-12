package pages;

import org.openqa.selenium.By;
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

    By ShippingMethodContButton = By.cssSelector("button.button-1.shipping-method-next-step-button");
    By PaymentMethodContButton = By.cssSelector("button.button-1.payment-method-next-step-button");
    By PaymentInfoContButton = By.cssSelector("button.button-1.payment-info-next-step-button");
    By ConfirmBtnLocator = By.cssSelector("button.confirm-order-next-step-button");
    By ConfirmationMsgLocator = By.xpath("//div[@class = 'section order-completed']//div[@class = 'title']//strong");

    public void FillCheckoutInformation(String CountryName, String City, String Address1, String ZipCode, String PhoneNum){
       BillingAddressObject.FillShippingInformation(CountryName, City, Address1, ZipCode, PhoneNum);
       actionObject.click(ShippingMethodContButton);
       actionObject.click(PaymentMethodContButton);
       actionObject.click(PaymentInfoContButton);
       actionObject.click(ConfirmBtnLocator);
    }

    public String getConfirmMessage(){
       return actionObject.getText(ConfirmationMsgLocator);
    }

}
