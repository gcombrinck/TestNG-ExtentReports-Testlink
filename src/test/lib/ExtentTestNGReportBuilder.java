package test.lib;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentTestNGReportBuilder extends TestLinkUtils{
    private final static String ACCESS_KEY = "7883f74761842243276add891144917a";
    private final static String TESTLINK_SERVER_URL = "http://apollo/testlink-1.9.7/lib/api/xmlrpc/v1/xmlrpc.php";
    private final static String TESTLINK_PROJECT_NAME = "Test Project";
    private final static String TESTLINK_TESTPLAN_NAME = "garth";
    private final static String BUILD_RELEASE_NAME = "automated build";
    private String testCaseExternalID;
    private String description;

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    @BeforeSuite
    public void beforeSuite() {
        extent = new ExtentReports();
        String date = new SimpleDateFormat("yyyyMMdd-hh-mm").format(new Date());
        String fileName = "./reports/"+date+"-results.html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.setAppendExisting(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("HealthQ System Test Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("HealthQ System Test Report");
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public synchronized void beforeClass() {
        ExtentTest parent = extent.createTest(getClass().getName());
        parentTest.set(parent);
    }

    @BeforeMethod
    public synchronized void beforeMethod(Method method) {
        ExtentTest child = parentTest.get().createNode(method.getName());
        test.set(child);
    }

    @Parameters("testlink")
    @AfterMethod
    public synchronized void afterMethod(ITestResult result, String testlink) {
        if (result.getStatus() == ITestResult.FAILURE)
            test.get().fail(result.getThrowable());
        else if (result.getStatus() == ITestResult.SKIP)
            test.get().skip(result.getThrowable());
        else
            test.get().pass("Test passed");

        extent.flush();

        if (testlink.equals("true") && testCaseExternalID != null) {
            URL url = null;
            try {
                url = new URL(TESTLINK_SERVER_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            TestLinkAPI api = new TestLinkAPI(url, ACCESS_KEY);
            System.out.println("Writing to Testlink");
            ExecutionStatus status;
            if (result.getStatus() == ITestResult.FAILURE) {
                status = ExecutionStatus.FAILED;
            } else if (result.getStatus() == ITestResult.SKIP) {
                status = ExecutionStatus.NOT_RUN;
            } else {
                status = ExecutionStatus.PASSED;
            }
            TestCase testcase = api.getTestCaseByExternalId(testCaseExternalID, 1);
            TestPlan testPlan = api.getTestPlanByName(TESTLINK_TESTPLAN_NAME, TESTLINK_PROJECT_NAME);
            int testPlanID = testPlan.getId();
            int testCaseId = testcase.getId();
            ReportTCResultResponse response = api.setTestCaseExecutionResult(testCaseId, null, testPlanID, status,
                    null, BUILD_RELEASE_NAME, description, true, null, null, null, null, true);
        }
    }

    public void setTestCaseId(String id) {
        this.testCaseExternalID = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTestCaseExternalID() {
        return testCaseExternalID;
    }

};
