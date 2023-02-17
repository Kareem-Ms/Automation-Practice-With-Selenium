package pages;

import io.qameta.allure.Step;
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

    @Step("Open home page")
    public void navigateToHomePage() {
        navigateToUrl(driver, url);
    }

    @Step("Open login page")
    public void openLoginPage() {
        openPage("ico-login");
    }

    @Step("Open my account page")
    public void openMyAccount() {
        openPage("ico-account");
    }

    @Step("Check if logout link exists")
    public String CheckLougoutLink() {
        return actionObject.getText(LogoutLinkLocator);
    }

    @Step("Open shopping cart from the link on notification")
    public void openNotificationShoppingCart() {
        actionObject.click(ShoppingCartNotificationLink);
    }

    public void openPage(String ClassName) {
        actionObject.click(By.className(ClassName));
    }

}
