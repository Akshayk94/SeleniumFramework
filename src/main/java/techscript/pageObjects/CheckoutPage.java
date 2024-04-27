package techscript.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import techscript.abstractComponents.AbstractComponent;

import java.util.List;

public class CheckoutPage extends AbstractComponent {
    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "input[placeholder*='Country']")
    WebElement country;
    @FindBy(css = "a[class*=submit]")
    WebElement placeOrder;
    @FindBy(xpath = "//div[@class='form-group']//section//span")
    List<WebElement> countryList;

    By countrySet= By.cssSelector("section[class*='ta-results']");

    public List<WebElement> getCountryList(){
        waitForElementToAppear(countrySet);
        return countryList;
    }
    public void selectCountry(String countryName,String input){
        country.sendKeys(input);
        getCountryList().stream()
                .filter(e->e.getText().equalsIgnoreCase(countryName))
                .findFirst().orElse(null)
                .click();
    }

    public ConfirmationPage placeOrder(){
        placeOrder.click();
        return new ConfirmationPage(driver);
    }
}
