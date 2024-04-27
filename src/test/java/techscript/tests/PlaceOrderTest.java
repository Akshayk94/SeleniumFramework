package techscript.tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import techscript.pageObjects.*;
import techscript.testComponents.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
//Positive Tests
public class PlaceOrderTest extends BaseTest {
    @Test(dataProvider = "getData", groups = "purchase")
    public void submitOrder(HashMap<String,String> input){
        /* visit landing page and login to app */
        ProductCatalogue catalogue=landingPage.loginToApp(input.get("email"), input.get("password"));
        /*
          get the list of all products from catalogue and
          add specific product into shopping cart
        */
        List<WebElement> items=catalogue.getProducts();
        catalogue.addProductToCart(input.get("product"));
        CartPage cartPage=catalogue.goToCartPage();
        /*
        * verify that product added to cart successfully
        * */
        boolean match=cartPage.verifyProductTitles(input.get("product"));
        Assert.assertTrue(match);
        CheckoutPage checkoutPage=cartPage.goToCheckout();
        /*
        * Place order by selecting specific country
        * */
        checkoutPage.selectCountry("INDIA","IND");
        ConfirmationPage confirmationPage=checkoutPage.placeOrder();

        String msg=confirmationPage.getConfirmation();
        Assert.assertTrue(msg.contains("ORDER"));
    }
    @Test(dependsOnMethods = {"submitOrder"})
    public void orderHistoryTest(){
        ProductCatalogue catalogue=landingPage.loginToApp("ark@gmail.com","Password@123");
        OrdersPage ordersPage=catalogue.goToOrdersPage();
        boolean match=ordersPage.verifyOrderDisplaying("ADIDAS");
        Assert.assertTrue(match);
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\techscript\\data\\purchase-order-data.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};
    }
}
