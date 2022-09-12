package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BrowserFactory {

    public static WebDriver driver ;

    public static WebDriver getDriver(String browserType){

        if(browserType.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
            driver = new ChromeDriver();
        }
        else if (browserType.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else if (browserType.equalsIgnoreCase("Edge")){
            System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"/drivers/msedgedriver.exe");
            driver = new EdgeDriver();
        }

        return driver;

    }
}
