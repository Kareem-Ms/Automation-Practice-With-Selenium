package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

//hna ana h3rf el webelemnts w el methods w bs h3ml call feh el tests lel methods deh
public class UserRegistrationPage {
    private final WebDriver driver;
    ElementActions actionObject;

    //da constructor lazm yt3ml
    public UserRegistrationPage(WebDriver driver) {
        this.driver = driver;
        actionObject = new ElementActions(driver);
    }


    //https://demo.nopcommerce.com/
    //hna el register 34an awslha mn el home page el mafrod any ados 3ala el link bta3 register el fo2 bs ana mlesh d3wa hena
    //el kalam da yt3ml feh page object 5asa be el home page

    //hagyeb el elements el hast5dmhom fe el register w l ykon el required fiields

    private By GendeRdobtnLocator = By.id("gender-male");

    private By FirstNameLocator = By.id("FirstName");

    private By LastNameLocator = By.id("LastName");

    private By EmailLocator = By.id("Email");

    private By PasswordLocator = By.id("Password");;

    private By ConfirmPasswordLocator = By.id("ConfirmPassword");

    private By RegisterbtnLocator = By.id("register-button");

    // we will need a method to register user with required fields
    //ana mesh 3ayz el data hna tb2a hard coded ana 3ayz el values tegy mn el test case 34an hayb2a feh test cases feha negative w positive scenarios
    //fa hnb3thom feh el parameter
    public void registerWithRequiredFields(String First, String Last, String Email, String Password , String Confirmation){


        actionObject.click(GendeRdobtnLocator);
        actionObject.type(FirstNameLocator, First);
        actionObject.type(LastNameLocator, Last);
        actionObject.type(EmailLocator, Email);
        actionObject.type(PasswordLocator, Password);
        actionObject.type(ConfirmPasswordLocator, Confirmation);
        actionObject.click(RegisterbtnLocator);
    }
}
