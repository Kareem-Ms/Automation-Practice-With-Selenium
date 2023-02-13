package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserRegisterResultPage;
import pages.UserRegistrationPage;
import utils.JsonFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getBrowser;


public class UserRegistrationTest {

    WebDriver driver;
    JsonFileManager jsonFileManager;
    HomePage homePage;
    UserRegistrationPage userRegistrationPage;
    UserRegisterResultPage userRegisterResultPage;
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());

    //hna hbd2 a7ot el test cases lel register
    @Test
    public void UserCanRegisterSucessfully() {
        //34an a3ml register ana m7tag aro7 el home b3d keda a3ml click 3ala register link
        //fa awl page object hst5dmha hya el home page
        //hna 3araf el homeObject w edaha el driver w el driver da el mwgod fe el TestBase class

        String email = jsonFileManager.getTestData("users.Email") + currentTime + "@"
                + jsonFileManager.getTestData("users.emailDomain");
        String password = jsonFileManager.getTestData("users.Password");

        //b3d keda ha3rf el Register object
        userRegistrationPage.registerWithRequiredFields(jsonFileManager.getTestData("users.firstname"),
                jsonFileManager.getTestData("users.LastName"), email, password, password);
        //we will check that the user is registered
        String msg = userRegisterResultPage.checkmsg();
        Assert.assertEquals(msg, jsonFileManager.getTestData("messages.RegisterSuccessfully"));
    }

    @BeforeMethod
    public void setUp() {
        jsonFileManager = new JsonFileManager("src/test/data/RegisterTestData.json");
        driver = getBrowser(jsonFileManager.getTestData("config.BrowserName"),
                jsonFileManager.getTestData("config.ExecutionType"));
        homePage = new HomePage(driver);
        userRegistrationPage = new UserRegistrationPage(driver);
        userRegisterResultPage = new UserRegisterResultPage(driver);
        userRegistrationPage.navigateToRegisterPage();
    }

    @AfterMethod
    public void tearDown() {
        closeAllBrowserTabs(driver);
    }

}
