package techscript.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import techscript.abstractComponents.AbstractComponent;

import java.util.List;

public class CartPage extends AbstractComponent {
    WebDriver driver;
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }
    @FindBy(css = ".infoWrap")
    List<WebElement> cartItems;
    @FindBy(xpath = "//button[text()='Checkout']")
    WebElement checkout;
    public Boolean verifyProductTitles(String productName){
        return cartItems.stream().anyMatch(e->e.getText().contains(productName));
    }

    public CheckoutPage goToCheckout(){
        checkout.click();
        return new CheckoutPage(driver);
    }
}
