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

    By registerLinkLocator = By.className("ico-register");

    By LoginLinkLocator = By.className("ico-login");

    By LogoutLinkLocator = By.className("ico-logout");

    By MyAccountLinkLocator = By.className("ico-account");

    public void navigateToHomePage(){
        navigateToUrl(driver,url);
    }

    //3ayz a3ml method b2a t3ml click 3ala el button da 34an ast5dmha fe el test cases bta3et el register
    public void openRegistrationPage (){
        actionObject.click(registerLinkLocator);
    }

    public void openLoginPage (){
        actionObject.click(LoginLinkLocator);
    }

    public String CheckLougoutLink(){
        return actionObject.getText(LogoutLinkLocator);
    }

    public void logOut(){
        actionObject.click(LogoutLinkLocator);
    }

    public void openMyAccount(){
        actionObject.click(MyAccountLinkLocator);
    }



}