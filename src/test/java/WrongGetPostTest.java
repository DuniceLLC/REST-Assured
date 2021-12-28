import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static io.restassured.RestAssured.*;

public class WrongGetPostTest {
    int correctPerPage = 7;
    int correctPage = 1;

    ErrorCode errorCode = new ErrorCode();
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void getPostWithoutPage() {
        Response response = given()
                .queryParam("perPage", correctPerPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT));
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue());
        softAssert.assertAll();
    }

    @Test
    public void getPostWithoutPerPage() {
        Response response = given()
                .queryParam("page", correctPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT));
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue());
        softAssert.assertAll();
    }

    @Test
    public void getPostWithPageEqual0() {
        Response response = given()
                .queryParam("page", 0)
                .queryParam("perPage", correctPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true");
        softAssert.assertTrue(codes.contains(errorCode.PARAM_PAGE_NOT_NULL));
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue());
        softAssert.assertAll();
    }

    @Test
    public void getPostWithPerPageEqual0() {
        Response response = given()
                .queryParam("page", correctPage)
                .queryParam("perPage", 0)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true");
        softAssert.assertTrue(codes.contains(errorCode.PARAM_PER_PAGE_NOT_NULL));
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue());
        softAssert.assertAll();
    }
}
