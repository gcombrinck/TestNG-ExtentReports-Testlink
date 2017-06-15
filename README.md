# TestNG-ExtentReports-Testlink
 *_created_ by gcombrinck*
### Tools used in this framework
1. [Java](https://www.java.com/en/)
```
We chose JAVA as the programming language to enable us to use a single programming language for all the tools.
```
2. [TestNG](http://testng.org/doc/)
```
TestNG is a testing framework inspired by JUnit and NUnit. TestNG is similar to JUnit. Few more functionalities are added to it that makes TestNG more powerful than JUnit.
```
| S.N.	| Description                                                                                      | TestNG                                               |
|-------|:-------------------------------------------------------------------------------------------------|:-----------------------------------------------------|
| 1   	| Test annotation	                                                                               | @Test                                                |
| 2   	| Executes before the first test method is invoked in the current class                            | @BeforeClass                                         |
| 3   	| Executes after all the test methods in the current class	                                       | @AfterClass                                          |
| 4   	| Executes before each test method	                                                               | @BeforeMethod                                        |
| 5   	| Executes after each test method	                                                               | @AfterMethod                                         |
| 6   	| annotation to ignore a test	                                                                   | @Test(enbale=false)                                  |
| 7   	| annotation for exception	                                                                       | @Test(expectedExceptions = ArithmeticException.class)|
| 8   	| timeout	                                                                                       | @Test(timeout = 1000)                                |
| 9   	| Executes before all tests in the suite 	                                                       | @BeforeSuite                                         |
| 10  	| Executes after all tests in the suite	                                                           | @AfterSuite                                          |
| 11  	| Executes before a test runs	                                                                   | @BeforeTest                                          |
| 12  	| Executes after a test runs                                                                       | @AfterTest                                           |
| 13  	| Executes before the first test method is invoked that belongs to any of these groups is invoked  | @BeforeGroups                                        |
| 14  	| run after the last test method that belongs to any of the groups here	                           | @AfterGroups                                         |
3. [Maven](https://maven.apache.org/)
```
Maven manages the dependencies and builds the project.
```
4. [RestAssured](http://rest-assured.io/)
```
REST-assured for testing REST-Api's.
```
5. [Selenium](http://www.seleniumhq.org/)
```
Selenium for web testing.
```
6. [Appium](http://appium.io/) //TODO
```
Appium for mobile testing.
```
7. [ExtentReports](http://extentreports.com/)
```
ExtentReports creates beautiful html reports.
```
8. [Testlink](http://www.testlink.org/)
```
Testlink tool is used to capture test case using its web interface. Test Results are update via the testlink api.
```

## Code Snippets
###### RestAssured
```java
public class HelloWorldRestAssured extends ExtentTestNGReportBuilder {
    @Test
    public void makeSureThatGoogleIsUp() {
        given()
                .when()
                .get("http://www.google.com")
                .then()
                .statusCode(200);
    }
}
```
###### Selenium
```java
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
```