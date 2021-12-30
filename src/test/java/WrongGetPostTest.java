import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import static io.restassured.RestAssured.*;

public class WrongGetPostTest {
    int correctPerPage = 7;
    int correctPage = 1;

    ErrorCode errorCode = new ErrorCode();

    @Epic("News-controller")
    @Feature("Get news")
    @Story("Without page")
    @Description(value = "Checking the correct server response")
    @Test
    public void getPostWithoutPage() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .queryParam("perPage", correctPerPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Get news")
    @Story("Without perPage")
    @Description(value = "Checking the correct server response")
    @Test
    public void getPostWithoutPerPage() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .queryParam("page", correctPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Get news")
    @Story("With page = 0")
    @Description(value = "Checking the correct server response")
    @Test
    public void getPostWithPageEqual0() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .queryParam("page", 0)
                .queryParam("perPage", correctPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true","Wrong \"success\"");
        try {
            softAssert.assertTrue(codes.contains(errorCode.PARAM_PAGE_NOT_NULL), "\"codes\" does not contain correct error code");
            softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }

    @Epic("News-controller")
    @Feature("Get news")
    @Story("With perPage = 0")
    @Description(value = "Checking the correct server response")
    @Test
    public void getPostWithPerPageEqual0() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .queryParam("page", correctPage)
                .queryParam("perPage", 0)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        try {
            softAssert.assertTrue(codes.contains(errorCode.PARAM_PER_PAGE_NOT_NULL), "\"codes\" does not contain correct error code");
            softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }
}
