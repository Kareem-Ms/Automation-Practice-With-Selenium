package pages;

import io.qameta.allure.Step;
import utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerInfoPage{

    WebDriver driver ;
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


    @Step("Open Change password page")
    public void openChangePassword(){
        actionsObject.click(ChangePasswordLinkLocator);
    }

    @Step("Change password using OldPassword: [0], NewPassword: [1], NewConfirmationPassword: [2]")
    public void ChangePassword(String OldPw ,String NewPw, String NewConfirmPw){
        actionsObject.type(OldPasswordLocator,OldPw);
        actionsObject.type(NewPasswordLocator,NewPw);
        actionsObject.type(ConfirmNewPasswordLocator,NewConfirmPw);
        actionsObject.click(ChangePasswordBtnLocator);
    }

    @Step("Get change password alert message")
    public String getConfirmationMessage(){
        return actionsObject.getText(ChangeConfirmationLocator);
    }

}
