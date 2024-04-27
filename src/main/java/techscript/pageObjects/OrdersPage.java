package techscript.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import techscript.abstractComponents.AbstractComponent;

import java.util.List;

public class OrdersPage extends AbstractComponent {
    WebDriver driver;
    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> cartItems;
    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public Boolean verifyOrderDisplaying(String productName){
        return cartItems.stream().anyMatch(p->p.getText().contains("ADIDAS"));
    }
}
