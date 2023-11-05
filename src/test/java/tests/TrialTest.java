package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class TrialTest {
    WebDriver driver;

    @Test
    public void testActions() throws InterruptedException {
        driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/forgot_password");
        WebElement field = driver.findElement(By.id("email"));
        new Actions(driver)
                .keyDown(Keys.SHIFT)
                .sendKeys(field,"Ahmed")
                .perform();

    }

    @Test
    public void copyAndPasteExample() throws InterruptedException {
        driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/forgot_password");
        WebElement field = driver.findElement(By.id("email"));

        new Actions(driver)
                .sendKeys(field, "MOhamed")
                .keyDown(Keys.CONTROL)
                .keyDown("a")
                .keyUp("a")
                .keyDown("c")
                .keyUp(Keys.CONTROL)
                .keyUp("c")
                .sendKeys(Keys.ARROW_RIGHT)
                .keyDown(Keys.CONTROL)
                .keyDown("v")
                .keyUp(Keys.CONTROL)
                .keyUp("v")
                .perform();

        Thread.sleep(1000);

    }

}
