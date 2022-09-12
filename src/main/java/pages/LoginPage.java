package pages;

import utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage{

    private final WebDriver driver;
    ElementActions actionObject;

    public LoginPage(WebDriver driver) {
       this.driver = driver;
        actionObject = new ElementActions(driver);
    }

    By  emailLocator = By.id("Email");

    By passwordLocator = By.id("Password");

    By LoginBtnLocator = By.cssSelector("button.button-1.login-button");

    By LoginErrorMessageLocator = By.cssSelector("div.message-error.validation-summary-errors");


    public void Login(String email, String password){
        actionObject.type(emailLocator,email);
        actionObject.type(passwordLocator,password);
        actionObject.click(LoginBtnLocator);
    }

    public String getLoginErrorMessage(){
        return actionObject.getText(LoginErrorMessageLocator);
    }

}
