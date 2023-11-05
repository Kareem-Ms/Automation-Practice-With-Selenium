package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class CheckoutBillingAddressPage {
    WebDriver driver;
    ElementActions actionObject;

    public CheckoutBillingAddressPage(WebDriver driver){
        this.driver = driver;
        actionObject = new ElementActions(driver);
    }

    By CountryDropdownLocator = By.id("BillingNewAddress_CountryId");
    By CityTextInputLocator = By.id("BillingNewAddress_City");
    By Address1InputTextLocator = By.id("BillingNewAddress_Address1");
    By ZipCodeInputTextLocator = By.id("BillingNewAddress_ZipPostalCode");
    By PhoneNumberInputTextLocator = By.id("BillingNewAddress_PhoneNumber");
    By ShippingContinueButton = By.xpath("//h2[text() = 'Billing address']//parent::div//following-sibling::div//div[@id = 'billing-buttons-container' ]//button[@name='save']");

    @Step("Fill checkout billing information with CountryName: [0],City: [1],Address: [2],ZipCode: [3],PhoneNumber: [4]")
    public void FillShippingInformation(String CountryName, String City, String Address1, String ZipCode, String PhoneNum ){
        actionObject.select(CountryDropdownLocator, ElementActions.SelectType.TEXT,CountryName);
        actionObject.type(CityTextInputLocator,City);
        actionObject.type(Address1InputTextLocator,Address1);
        actionObject.type(ZipCodeInputTextLocator,ZipCode);
        actionObject.type(PhoneNumberInputTextLocator,PhoneNum);
        actionObject.click(ShippingContinueButton);
    }


}
