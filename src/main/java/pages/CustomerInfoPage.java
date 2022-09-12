package pages;

import utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerInfoPage{

    private WebDriver driver ;
    ElementActions actionsObject;

    public CustomerInfoPage(WebDriver driver) {
        this.driver = driver;
        actionsObject = new ElementActions(driver);
    }

    By ChangePasswordLinkLocator = By.linkText("Change password");

    By OldPasswordLocator = By.id("OldPassword");

    By NewPasswordLocator = By.id("NewPassword");

    By ConfirmNewPasswordLocator = By.id("ConfirmNewPassword");

    By ChangePasswordBtnLocator = By.cssSelector("button.button-1.change-password-button");

    By ChangeConfirmationLocator = By.cssSelector("p.content");


    public void openChangePassword(){
        actionsObject.click(ChangePasswordLinkLocator);
    }

    public void ChangePassword(String OldPw ,String NewPw, String NewConfirmPw){
        actionsObject.type(OldPasswordLocator,OldPw);
        actionsObject.type(NewPasswordLocator,NewPw);
        actionsObject.type(ConfirmNewPasswordLocator,NewConfirmPw);
        actionsObject.click(ChangePasswordBtnLocator);
    }

    public String getConfirmationMessage(){
        return actionsObject.getText(ChangeConfirmationLocator);
    }

}
