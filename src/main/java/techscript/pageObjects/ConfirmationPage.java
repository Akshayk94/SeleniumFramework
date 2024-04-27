package techscript.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import techscript.abstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {
    WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    @FindBy(css = ".hero-primary")
    WebElement confirmation;

    public String getConfirmation(){
        return confirmation.getText();
    }
}
