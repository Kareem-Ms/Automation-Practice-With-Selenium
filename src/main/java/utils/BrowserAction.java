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
            //this line make sure that the page is completely loaded
            //so here we return the status and make sure that it equals complete so that we make sure that the page is loaded
            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
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
