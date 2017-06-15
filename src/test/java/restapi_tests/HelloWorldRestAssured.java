package restapi_tests;

import static io.restassured.RestAssured.given;

import lib.ExtentTestNGReportBuilder;
import org.testng.annotations.*;


/**
 * Created by garth.combrinck on 2017/06/07.
 */

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
