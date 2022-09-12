package utils;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;


public class ElementActions {
    private WebDriver driver;

    public enum SelectType {
        TEXT, VALUE;
    }

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    public static WebDriverWait getExplicitWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //    @Step("Click on element: [{elementLocator}]")
    public static void click(WebDriver driver, By elementLocator) {
        // Mouse hover on the element before clicking
        mouseHover(driver, elementLocator);
        try {
            // wait for the element to be clickable
            getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (TimeoutException toe) {
            fail(toe.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            // Now we click on the element! :D
            driver.findElement(elementLocator).click();
        } catch (Exception exception) {
            // Click using JavascriptExecutor in case of the click is not performed
            // successfully and got an exception using the Selenium click method
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].click();",
                        driver.findElement(elementLocator));
            } catch (Exception rootCauseException) {
                rootCauseException.initCause(exception);
                // Force fail the test case if couldn't perform the click
                fail("Couldn't click on the element:" + elementLocator, rootCauseException);
            }
        }
    }

    public ElementActions click(By elementLocator) {
        click(driver, elementLocator);
        return this;
    }

    public static void type(WebDriver driver, By elementLocator, String text) {
        type(driver, elementLocator, text, true);
    }

    //    @Step("Type data: [{data}] on element: [{elementLocator}]")
    public static void type(WebDriver driver, By elementLocator, String text, boolean clearBeforeTyping) {
        locatingElementStrategy(driver, elementLocator);
        try {
            // Clear before typing condition
            if (!driver.findElement(elementLocator).getAttribute("value").isEmpty() && clearBeforeTyping) {
                driver.findElement(elementLocator).clear();
                // We type here! :D
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                // using the Selenium sendKeys method
                if (!driver.findElement(elementLocator).getAttribute("value").equals(text)) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')",
                            driver.findElement(elementLocator));
                }
            } else {
                // We type here! :D
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                // using the Selenium sendKeys method
                if (!driver.findElement(elementLocator).getAttribute("value").contains(text)) {
                    String currentValue = driver.findElement(elementLocator).getAttribute("value");
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].setAttribute('value', '" + currentValue + text + "')",
                            driver.findElement(elementLocator));
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
        // Make sure that the data is inserted correctly to the field
        Assert.assertTrue(driver.findElement(elementLocator).getAttribute("value").contains(text),
                "The data is not inserted successfully to the field, the inserted data should be: [" + text
                        + "]; But the current field value is: ["
                        + driver.findElement(elementLocator).getAttribute("value") + "]");
    }

    public ElementActions type(By elementLocator, String text) {
        type(driver, elementLocator, text, true);
        return this;
    }

    public ElementActions type(By elementLocator, String text, boolean clearBeforeTyping) {
        type(driver, elementLocator, text, clearBeforeTyping);
        return this;
    }

    public static void select(WebDriver driver, By elementLocator, SelectType selectType, String option) {
        locatingElementStrategy(driver, elementLocator);
        try {
            Select s = new Select(driver.findElement(elementLocator));
            assertFalse(s.isMultiple());
            if(selectType == SelectType.TEXT){
                s.selectByVisibleText(option);
            }
            else if (selectType == SelectType.VALUE){
                s.selectByValue(option);
            }

        } catch (Exception e) {

            fail(e.getMessage());
        }
    }

    public ElementActions select(By elementLocator, SelectType selectType, String option) {
        select(driver, elementLocator, selectType, option);
        return this;
    }

    public static void mouseHover(WebDriver driver, By elementLocator) {
        locatingElementStrategy(driver, elementLocator);
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(elementLocator)).perform();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public ElementActions mouseHover(By elementLocator) {
        mouseHover(driver, elementLocator);
        return this;
    }

    public static void doubleClick(WebDriver driver, By elementLocator) {
        locatingElementStrategy(driver, elementLocator);
        try {
            Actions actions = new Actions(driver);
            actions.doubleClick(driver.findElement(elementLocator)).perform();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public ElementActions doubleClick(By elementLocator) {
        doubleClick(driver, elementLocator);
        return this;
    }

    //  @Step("Click a Keyboard Key on element: [{elementLocator}]")
    public static void clickKeyboardKey(WebDriver driver, By elementLocator, Keys key) {
        locatingElementStrategy(driver, elementLocator);
        try {

            // We click ENTER here! :D
            driver.findElement(elementLocator).sendKeys(key);
        } catch (Exception e) {

        }
    }

    public ElementActions clickKeyboardKey(By elementLocator, Keys key) {
        clickKeyboardKey(driver, elementLocator, key);
        return this;
    }

    //    @Step("Get the Text of element: [{elementLocator}]")
    public static String getText(WebDriver driver, By elementLocator) {
        locatingElementStrategy(driver, elementLocator);
        try {
            String text = driver.findElement(elementLocator).getText();
            return text;
        } catch (Exception e) {
        }
        return null;
    }

    public String getText(By elementLocator){
       return getText(driver, elementLocator);
    }





    public static String getAttributeValue(WebDriver driver, By elementLocator, String attributeName) {
        locatingElementStrategy(driver, elementLocator);
        try {
            String attributeValue = driver.findElement(elementLocator).getAttribute(attributeName);

            return attributeValue;
        } catch (Exception e) {

        }
        return null;
    }

    public static void SelectFromList(WebDriver driver, By elementLocator, int index){
        locatingElementStrategy(driver , elementLocator);
        try {
            List<WebElement> list = driver.findElements(elementLocator);
            list.get(index).click();
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

   public void SelectFromList(By elementLocator, int index){
       SelectFromList(driver,elementLocator,index);
   }



    private static void locatingElementStrategy(WebDriver driver, By elementLocator) {
        try {
            // Wait for the element to be visible
            getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            // Scroll the element into view to handle some browsers cases
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
                    driver.findElement(elementLocator));
            // Check if the element is displayed
            if (!driver.findElement(elementLocator).isDisplayed()) {
                fail("The element [" + elementLocator.toString() + "] is not Displayed");
            }
        } catch (TimeoutException toe) {
            fail(toe.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}