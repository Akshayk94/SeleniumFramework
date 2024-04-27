package techscript.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import techscript.abstractComponents.AbstractComponent;

import java.util.List;

public class ProductCatalogue extends AbstractComponent {
    WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='card']")
    List<WebElement> products;

    By productsBy=By.xpath("//div[@class='card']");
    By addToCart=By.cssSelector(".card i[class*='shopping-cart']");
    By toastMessage=By.id("toast-container");
    By spinner=By.cssSelector(".ng-animating");

    public List<WebElement> getProducts(){
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName){
        return products.stream().filter(i -> i.findElement(By.tagName("b"))
                .getText().contains(productName)).findFirst().orElse(null);
    }

    public void addProductToCart(String productName){
        WebElement product=getProductByName(productName);
        product.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);
    }
}
