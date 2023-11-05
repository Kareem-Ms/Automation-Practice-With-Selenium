package pages;

import io.qameta.allure.Step;
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

   @Step("Search for product using it's complete name")
   public void searchForProductCompleteName(String product_name){
       actionsObject.type(SearchfieldLocator,product_name);
       actionsObject.click(SearchButtonLocator);
   }

   @Step("Search for product using part of it's name")
   public void searchProductAutoComplete(String abbreviation,int index){
       actionsObject.type(SearchfieldLocator,abbreviation);
       actionsObject.SelectFromList(AutoCompleteListLocator,index);
   }

   @Step("Open details of [0] product")
   public void openProjectDetailsPage(String ProductName){
       String ProductLinkxpath = "//h2[@class ='product-title']//a[text()='"+ProductName+"']";
       actionsObject.click(By.xpath(ProductLinkxpath));
   }

}
