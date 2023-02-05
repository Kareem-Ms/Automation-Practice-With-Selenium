package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class UserRegisterResultPage{

    WebDriver driver ;
    ElementActions actionObject;

    public UserRegisterResultPage(WebDriver driver) {
        this.driver= driver;
        actionObject = new ElementActions(driver);
    }

    public String checkmsg(){
        return actionObject.getText(By.className("result"));
    }
}
