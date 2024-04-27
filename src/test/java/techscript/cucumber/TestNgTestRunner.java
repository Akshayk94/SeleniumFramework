package techscript.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/techscript/cucumber",
        glue ="techscript.stepdefinitions",
        monochrome = true,
        tags = "@Regression",
        plugin = {"pretty","html:target/cucumber.html"})
public class TestNgTestRunner extends AbstractTestNGCucumberTests {

}
