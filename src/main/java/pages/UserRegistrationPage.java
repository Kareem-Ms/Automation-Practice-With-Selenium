package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

//hna ana h3rf el webelemnts w el methods w bs h3ml call feh el tests lel methods deh
public class UserRegistrationPage {

    WebDriver driver;
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

    // we will need a method to register user with required fields
    //ana mesh 3ayz el data hna tb2a hard coded ana 3ayz el values tegy mn el test case 34an hayb2a feh test cases feha negative w positive scenarios
    //fa hnb3thom feh el parameter
    public void registerWithRequiredFields(String First, String Last, String Email, String Password , String Confirmation){
        actionObject.click(getLocatorByID("gender-male"));
        actionObject.type(getLocatorByID("FirstName"), First);
        actionObject.type(getLocatorByID("LastName"), Last);
        actionObject.type(getLocatorByID("Email"), Email);
        actionObject.type(getLocatorByID("Password"), Password);
        actionObject.type(getLocatorByID("ConfirmPassword"), Confirmation);
        actionObject.click(getLocatorByID("register-button"));
    }

    private By getLocatorByID(String ID){
        return By.id(ID);
    }

}
