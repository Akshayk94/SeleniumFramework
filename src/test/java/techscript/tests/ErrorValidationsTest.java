package techscript.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import techscript.pageObjects.CartPage;
import techscript.pageObjects.ProductCatalogue;
import techscript.testComponents.BaseTest;
import techscript.testComponents.Retry;

import java.util.List;

//Negative Tests
public class ErrorValidationsTest extends BaseTest {
    @Test(groups = {"ErrorHandling"},retryAnalyzer = Retry.class)
    public void loginErrorValidation(){
        ProductCatalogue catalogue=landingPage.loginToApp("myemail@gmail.com","pass123");
        Assert.assertEquals(landingPage.getErrorMessage(),"Incorrect email or password.");
    }
    @Test(groups = {"ErrorHandling"})
    public void productErrorValidation(){
        ProductCatalogue catalogue=landingPage.loginToApp("ark@gmail.com","Password@123");
        List<WebElement> items=catalogue.getProducts();
        catalogue.addProductToCart("ADIDAS");
        CartPage cartPage=catalogue.goToCartPage();
        boolean match=cartPage.verifyProductTitles("ADIDASSS");
        Assert.assertFalse(match);
    }
}
