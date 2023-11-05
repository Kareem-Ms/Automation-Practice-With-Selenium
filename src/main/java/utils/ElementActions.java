package utils;

import static org.testng.Assert.fail;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class ElementActions {
    private final WebDriver driver;

    public enum SelectType {
        TEXT, VALUE
    }

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait getExplicitWait(WebDriver driver) {
        return new WebDriverWait(driver,Duration.ofSeconds(10));
    }

    public void click(By elementLocator) {
        // Mouse hover on the element before clicking
        mouseHover(elementLocator);

        // Check if the element is clickable
        try {
            // wait for the element to be clickable
            getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (TimeoutException toe) {
            // If the element doesn't become clickable the test case will fail
            fail(toe.getMessage());
        }

        // Try to click on the element
        try {
            // Now we click on the element with selenium click method
            driver.findElement(elementLocator).click();
        } catch (Exception exception) {
            // Click on element using JavascriptExecutor in case of the click is not performed
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                        driver.findElement(elementLocator));
            } catch (Exception e) {
                // Force fail the test case if we couldn't perform the click
                fail("Couldn't click on the element:" + elementLocator, e);
            }
        }
    }

    public void type(By elementLocator, String text) {
        locatingElement(elementLocator);
        try {
                // Clear the input field
                driver.findElement(elementLocator).clear();

                // We type here!
                driver.findElement(elementLocator).sendKeys(text);

                // Check if the text wasn't typed successfully
                if (!driver.findElement(elementLocator).getAttribute("value").contains(text)) {
                    // If it wasn't we try to type using JavascriptExecutor
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].setAttribute('value', '" + text + "')",
                            driver.findElement(elementLocator));
                }

        } catch (Exception e) {
            // Force fail the test case if we couldn't type into the element
            fail(e.getMessage());
        }

    }

    public void select(By elementLocator, SelectType selectType, String option) {
        locatingElement(elementLocator);
        try {
            Select s = new Select(driver.findElement(elementLocator));

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


    public void mouseHover(By elementLocator) {
        // Make sure that the element is displayed
        locatingElement(elementLocator);
        try {
            // Now we hover on the element
            new Actions(driver)
                    .moveToElement(driver.findElement(elementLocator))
                    .perform();
        } catch (Exception e) {
            // If we can't hover on the element then the test case will fail
            fail(e.getMessage());
        }
    }

    public  void doubleClick(By elementLocator) {
        locatingElement(elementLocator);
        try {
            new Actions(driver)
                    .doubleClick(driver.findElement(elementLocator))
                    .perform();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public String getText(By elementLocator) {
        locatingElement(elementLocator);
        try {
            return driver.findElement(elementLocator).getText();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return null;
    }

    public String getAccessibleName(By elementLocator) {
        locatingElement(elementLocator);
        try {
            return driver.findElement(elementLocator).getAccessibleName();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return null;
    }

    public String getAttributeValue(By elementLocator, String attributeName) {
        locatingElement(elementLocator);
        try {
            return driver.findElement(elementLocator).getAttribute(attributeName);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return null;
    }

    public  void SelectFromList(By elementLocator, int index){
        locatingElement(elementLocator);
        try {
            List<WebElement> list = driver.findElements(elementLocator);
            list.get(index).click();
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    //This method is used to make sure that the element is visible and displayed so that we could take any action on it
    private void locatingElement(By elementLocator) {
        try {
            // Wait for the element to be visible
            getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            // Check if the element is displayed
            if (!driver.findElement(elementLocator).isDisplayed()) {
                fail("The element [" + elementLocator.toString() + "] is not Displayed");
            }
        } catch (TimeoutException toe) {
            fail(toe.getMessage());
        }
    }

}