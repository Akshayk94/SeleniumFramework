package techscript.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import techscript.pageObjects.LandingPage;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class StandAloneTest {
    public static void main(String[] args) {
        WebDriver driver=new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        LandingPage landPage=new LandingPage(driver);

        driver.findElement(By.id("userEmail")).sendKeys("ark@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Password@123");
        driver.findElement(By.id("login")).click();
        /*Wait until products get displayed*/
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='card']")));
        /*Fetch all the products*/
        List<WebElement> items =driver.findElements(By.xpath("//div[@class='card']"));
        /*Add Specific Product into the cart*/
        items.stream().filter(i -> i.findElement(By.tagName("b")).getText().contains("ADIDAS"))
                .map(i -> i.findElement(By.cssSelector(".card i[class*='shopping-cart']")))
                .findFirst().ifPresent(WebElement::click);
        /*Wait until product added to cart toast message is displayed*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
        /*Click on add to cart button*/
        driver.findElement(By.cssSelector("[routerlink='/dashboard/cart']")).click();
        /*Fetch all cart items*/
        //CASE-1 Checkout by clicking on Checkout button
        /*List<WebElement> cartItems=driver.findElements(By.cssSelector(".cartSection h3"));
        boolean found=cartItems.stream().anyMatch(e->e.getText().contains("ADIDAS"));
        Assert.assertTrue(found);
        driver.findElement(By.xpath("//button[text()='Checkout']")).click();*/

        //CASE-2 Checkout by clicking on Buy Now button
        List<WebElement> purchaseItems=driver.findElements(By.cssSelector(".infoWrap"));
        purchaseItems.stream().
                filter(e->e.findElement(By.cssSelector("h3")).getText().contains("ADIDAS")).
                map(e->e.findElement(By.cssSelector(".btn-primary"))).
                findFirst()
                .ifPresent(WebElement::click);
        /*Enter the necessary details and place order*/
        driver.findElement(By.cssSelector("input[placeholder*='Country']")).sendKeys("IND");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[class*='ta-results']")));
        WebElement list=driver.findElement(By.cssSelector("section[class*=ta-results]"));
        List<WebElement> countries=list.findElements(By.xpath("//div[@class='form-group']//section//span"));
        countries.stream()
                .filter(e->e.getText().equalsIgnoreCase("India"))
                .findFirst().orElse(null)
                .click();

        driver.findElement(By.cssSelector("a[class*=submit]")).click();
        String msg=driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(msg.contains("ORDER"));
    }
}
