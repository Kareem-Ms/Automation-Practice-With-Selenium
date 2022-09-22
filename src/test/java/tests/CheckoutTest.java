package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import utils.JsonFileManager;

import static utils.BrowserFactory.getDriver;

public class CheckoutTest {

    WebDriver driver;
    JsonFileManager JsonObject;



    @BeforeClass
    public void setUp(){
        JsonObject = new JsonFileManager("");
        driver = getDriver("chrome");

    }
}
