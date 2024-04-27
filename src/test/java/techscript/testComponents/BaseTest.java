package techscript.testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import techscript.pageObjects.LandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    public WebDriver initializeDriver() throws IOException {
        Properties prop=new Properties();
        FileInputStream fin=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/techscript/resources/application.properties");
        prop.load(fin);
        //accept browser name as maven parameter from cmd
        //If user is not providing parameter then use properties file details
        String browser=System.getProperty("browser")!=null?System.getProperty("browser"):prop.getProperty("browser");
        driver = switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            case "safari" -> new SafariDriver();
            default -> null;
        };
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        landingPage=new LandingPage(initializeDriver());
        landingPage.goToLandingPage();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void shutDown(){
        driver.close();
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        //read json to string
        String jsonContent= FileUtils.readFileToString(new File(filePath));
        //String to hashmap (dependency= jackson databind)
        ObjectMapper mapper=new ObjectMapper();
        List<HashMap<String,String>> data=
                mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
        return data;
    }

    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
        TakesScreenshot ts=(TakesScreenshot)driver;
        File screenshot=ts.getScreenshotAs(OutputType.FILE);
        File file=new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
        FileUtils.copyFile(screenshot,file);
        return testCaseName+".png";
    }
}
