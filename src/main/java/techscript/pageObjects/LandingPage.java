package techscript.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import techscript.abstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
    WebDriver driver;
    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    //Page factory
    @FindBy(id="userEmail")
    WebElement userEmail;
    @FindBy(css = "#userPassword")
    WebElement userPassword;
    @FindBy(id = "login")
    WebElement login;
    @FindBy(xpath = "//div[@aria-label='Incorrect email or password.']")
    WebElement errorMessage;

    public ProductCatalogue loginToApp(String email, String password){
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        ProductCatalogue catalogue=new ProductCatalogue(driver);
        login.click();
        return catalogue;
    }

    public void goToLandingPage(){
        driver.get("https://rahulshettyacademy.com/client");
    }

    public String getErrorMessage(){
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

}
