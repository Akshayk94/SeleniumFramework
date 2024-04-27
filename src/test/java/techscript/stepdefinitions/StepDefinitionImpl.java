package techscript.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import techscript.pageObjects.*;
import techscript.testComponents.BaseTest;

import java.io.IOException;
import java.util.List;

public class StepDefinitionImpl extends BaseTest {
    public LandingPage landingPage;
    public ProductCatalogue catalogue;
    public  ConfirmationPage confirmationPage;
    @Given("I landed on Ecommerce page")
    public void I_landed_on_Ecommerce_page() throws IOException {
        landingPage=launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_and_password(String username,String password){
        catalogue=landingPage.loginToApp(username, password);
    }

    @When("^I add product (.+) to cart$")
    public void add_product_to_cart(String productName){
        List<WebElement> items=catalogue.getProducts();
        catalogue.addProductToCart(productName  );
    }

    @When("^Checkout (.+) and submit the order$")
    public void checkout_submit_order(String productName){
        CartPage cartPage=catalogue.goToCartPage();
        boolean match=cartPage.verifyProductTitles(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage=cartPage.goToCheckout();
        checkoutPage.selectCountry("INDIA","IND");
        confirmationPage=checkoutPage.placeOrder();
    }

    @Then("{string} message is displayed on Confirmation page")
    public void message_displayed_confirmationPage(String string){
        String msg=confirmationPage.getConfirmation();
        Assert.assertTrue(msg.contains(string));
        driver.close();
    }

    @Then("{string} message is displayed")
    public void messageIsDisplayed(String msg) {
        Assert.assertEquals(landingPage.getErrorMessage(),msg);
        driver.close();
    }
}
