package techscript.abstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import techscript.pageObjects.CartPage;
import techscript.pageObjects.OrdersPage;

import java.time.Duration;

public class AbstractComponent {
    WebDriver driver;
    @FindBy(css = "[routerlink='/dashboard/cart']")
    WebElement addToCartIcon;
    @FindBy(css = "[routerlink='/dashboard/myorders']")
    WebElement ordersPage;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void waitForElementToAppear(By findBy){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForWebElementToAppear(WebElement findBy){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }

    public void waitForElementToDisappear(By findBy){
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(2));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
    }

    public CartPage goToCartPage(){
        addToCartIcon.click();
        return new CartPage(driver);
    }

    public OrdersPage goToOrdersPage(){
        ordersPage.click();
        return new OrdersPage(driver);
    }
}
