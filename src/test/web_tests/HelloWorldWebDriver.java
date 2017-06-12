package test.web_tests;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import test.lib.ExcelUtils;
import test.lib.ExtentTestNGReportBuilder;
import test.web_tests.pages.Home_Page;
import test.web_tests.pages.Login_Page;


/**
 * Created by garth.combrinck on 2017/06/07.
 */
public class HelloWorldWebDriver extends ExtentTestNGReportBuilder {
    public static String BaseUrl = "http://store.demoqa.com/";
    public static WebDriver driver;
    public static String FILE_NAME = System.getProperty("user.dir") + "/src/test/data/login_details.xlsx";
    @BeforeMethod
    public void setUp() throws Exception {
        DOMConfigurator.configure("log4j.xml");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/lib/chromedriver_2.29.exe");
        driver = new ChromeDriver();
    }
    @Test(dataProvider = "Authentication")
    public void testLoginPage(String sUserName,String sPassword) throws Exception {
        driver.get(BaseUrl);
        Home_Page.lnk_MyAccount(driver).click();
        Login_Page.txtbx_UserName(driver).sendKeys(sUserName);
        Login_Page.txtbx_Password(driver).sendKeys(sPassword);
        Login_Page.btn_LogIn(driver).click();
        System.out.println(" Login Successfully, now it is the time to Log Off buddy.");
        Home_Page.lnk_LogOut(driver).click();
    }
    @DataProvider
    public Object[][] Authentication() throws Exception{
        Object[][] testObjArray = ExcelUtils.getTableArray(FILE_NAME,"Sheet1");
        return (testObjArray);
    }
    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }
}
