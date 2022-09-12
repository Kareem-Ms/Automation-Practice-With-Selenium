package pages;

import utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage {

    WebDriver driver;
    ElementActions actionsObject;

    public  SearchPage(WebDriver driver){
        this.driver = driver;
        actionsObject = new ElementActions(driver);
    }

   By SearchfieldLocator = By.id("small-searchterms");
   By SearchButtonLocator = By.cssSelector("button.button-1.search-box-button");
   By AutoCompleteListLocator = By.id("ui-id-1");

   public void searchForProductCompleteName(String product_name){
       actionsObject.type(SearchfieldLocator,product_name);
       actionsObject.click(SearchButtonLocator);
   }

   public void searchProductAutoComplete(String abbreviation,int index){
       actionsObject.type(SearchfieldLocator,abbreviation);
       actionsObject.SelectFromList(AutoCompleteListLocator,index);
   }
}
