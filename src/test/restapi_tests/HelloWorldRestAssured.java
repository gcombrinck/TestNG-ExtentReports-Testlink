package test.restapi_tests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.*;
import test.lib.ExtentTestNGReportBuilder;
import test.lib.*;

import java.net.MalformedURLException;

/**
 * Created by garth.combrinck on 2017/06/07.
 */

public class HelloWorldRestAssured extends ExtentTestNGReportBuilder {
    @Parameters("ui")
    @Test
    public void makeSureThatGoogleIsUp() {
        setTestCaseId("TST-1");
        setDescription("This is the description 1000");
        given().when().get("http://www.google.com").then().statusCode(200);
    }
}
