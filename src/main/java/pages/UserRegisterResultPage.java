package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class UserRegisterResultPage{

    private final WebDriver driver ;
    ElementActions actionObject;

    public UserRegisterResultPage(WebDriver driver) {
        this.driver= driver;
        actionObject = new ElementActions(driver);
    }

    By RegisterResultMsg = By.className("result");


    public String checkmsg(){
        return actionObject.getText(RegisterResultMsg);
    }
}
