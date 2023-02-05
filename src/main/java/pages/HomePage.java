package pages;

import utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static utils.BrowserAction.navigateToUrl;

public class HomePage {

    private final WebDriver driver;
    ElementActions actionObject;
    String url = "https://demo.nopcommerce.com/";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        actionObject = new ElementActions(driver);
    }

    By LogoutLinkLocator = By.className("ico-logout");
    By ShoppingCartNotificationLink = By.xpath("//p[@class = 'content']//a[text() ='shopping cart' ]");

    public void navigateToHomePage() {
        navigateToUrl(driver, url);
    }

    //3ayz a3ml method b2a t3ml click 3ala el button da 34an ast5dmha fe el test cases bta3et el register
    public void openRegistrationPage() {
        openPage("ico-register");
    }

    public void openLoginPage() {
        openPage("ico-login");
    }

    public void openMyAccount() {
        openPage("ico-account");
    }

    public String CheckLougoutLink() {
        return actionObject.getText(LogoutLinkLocator);
    }

    public void openNotificationShoppingCart() {
        actionObject.click(ShoppingCartNotificationLink);
    }

    public void openPage(String ClassName) {
        actionObject.click(By.className(ClassName));
    }

}
