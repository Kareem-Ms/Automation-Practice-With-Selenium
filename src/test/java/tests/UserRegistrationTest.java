package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserRegisterResultPage;
import pages.UserRegistrationPage;

import static utils.BrowserAction.closeAllBrowserTabs;
import static utils.BrowserFactory.getDriver;

public class UserRegistrationTest {

    WebDriver driver;

    HomePage homeObject;
    UserRegistrationPage RegisterObject ;
    UserRegisterResultPage ResultObject;

    //hna hbd2 a7ot el test cases lel register
    @Test
    public void UserCanRegisterSucessfully(){
        //34an a3ml register ana m7tag aro7 el home b3d keda a3ml click 3ala register link
        //fa awl page object hst5dmha hya el home page
        //hna 3araf el homeObject w edaha el driver w el driver da el mwgod fe el TestBase class

        //keda ana dost 3ala el link
        homeObject.openRegistrationPage();
        //b3d keda ha3rf el Register object
        RegisterObject.registerWithRequiredFields("Mohamed","Ahmed","Mohamedr@gmail.com","tester123","tester123");
        //we will check that the user is registered
        String msg = ResultObject.checkmsg();
        Assert.assertEquals(msg,"Your registration completed");
    }

    @BeforeMethod
    public void startup(){
        driver = getDriver("chrome");
        homeObject = new HomePage(driver);
        RegisterObject = new UserRegistrationPage(driver);
        ResultObject = new UserRegisterResultPage(driver);

        homeObject.navigateToHomePage();

    }

    @AfterMethod
    public void closeUp(){
        closeAllBrowserTabs(driver);
    }

}
