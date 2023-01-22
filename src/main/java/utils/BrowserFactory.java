package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserFactory {

   public static WebDriver driver;

    public static WebDriver getBrowser(String BrowserName, String ExecutionType)  {
        if(ExecutionType.equalsIgnoreCase("remote")){
            if(BrowserName.equalsIgnoreCase("chrome")){

                ChromeOptions options = new ChromeOptions();
               // options.addArguments("headless");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                options.merge(capabilities);
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            else if (BrowserName.equalsIgnoreCase("firefox")){
                FirefoxOptions options = new FirefoxOptions();
                //options.addArguments("headless");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
                options.merge(capabilities);
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }
        else if (ExecutionType.equalsIgnoreCase("local")){
            if(BrowserName.equalsIgnoreCase("chrome")){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            else if (BrowserName.equalsIgnoreCase("firefox")){
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
        }


        return driver;
    }
}
