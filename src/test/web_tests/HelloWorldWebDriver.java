package test.web_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.lib.ExtentTestNGReportBuilder;

import static io.restassured.RestAssured.given;

/**
 * Created by garth.combrinck on 2017/06/07.
 */
public class HelloWorldWebDriver extends ExtentTestNGReportBuilder {
    public static String baseUrl = "http://google.com";
    public static WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/lib/chromedriver_2.29.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void makeSureThatGoogleIsUp(){
        driver.get(baseUrl);
        Assert.assertEquals(driver.getTitle(),"Google");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }
}
