package test.lib;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class TestLinkUtils extends TestLinkAPIException {
    private final static String ACCESS_KEY = "7883f74761842243276add891144917a";
    private final static String TESTLINK_SERVER_URL = "http://apollo/testlink-1.9.7/lib/api/xmlrpc/v1/xmlrpc.php";
    private final static String TESTLINK_PROJECT_NAME = "Test Project";
    private final static String TESTLINK_TESTPLAN_NAME = "garth";
    private final static String BUILD_RELEASE_NAME = "Test build";
    private String testCaseExternalID;
    private String description;

    @AfterMethod
    public synchronized void afterMethod(ITestResult result, String testlink) throws MalformedURLException {
        URL url = new URL(TESTLINK_SERVER_URL);
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
}
