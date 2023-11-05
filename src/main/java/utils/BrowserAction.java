package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import static org.testng.AssertJUnit.fail;

public class BrowserAction {


    public static void navigateToUrl (WebDriver driver, String url){
        try {
            driver.manage().window().maximize();
            driver.get(url);
        }catch (Exception e){
            //will make the test cases fail if we couldn't load the page
            fail(e.getMessage());
        }
    }

    public static void closeAllBrowserTabs(WebDriver driver){
        //firstly we check if the driver is null it might be already closed
        if(driver != null){
         //then we try to close it
            try {
                driver.quit();
            }catch (WebDriverException rootCauseException){
                System.out.println(rootCauseException.getMessage());
            }
        }else{
            //we print that the driver already closed
            System.out.println("Driver is already closed and driver object is null");
        }
    }
}
