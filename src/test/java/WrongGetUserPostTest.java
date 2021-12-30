import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class WrongGetUserPostTest extends GetPostTest {
    int correctPerPage = 7;
    int correctPage = 1;
    ErrorCode errorCode = new ErrorCode();

    @Epic("News-controller")
    @Feature("Get user news")
    @Story("Without page")
    @Description(value = "Checking the correct server response")
    @Test
    public void getUserPostWithoutPage() {
        Response responseAfterGetPost = given()
                .header("Authorization", token)
                .queryParam("perPage", correctPerPage)
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseAfterGetPost.jsonPath().getString("success");
        int customStatusCode = responseAfterGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterGetPost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Get user news")
    @Story("Without perPage")
    @Description(value = "Checking the correct server response")
    @Test
    public void getUserPostWithoutPerPage() {
        Response responseAfterGetPost = given()
                .header("Authorization", token)
                .queryParam("page", correctPage)
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseAfterGetPost.jsonPath().getString("success");
        int customStatusCode = responseAfterGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterGetPost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Get user news")
    @Story("Without token")
    @Description(value = "Checking the correct server response")
    @Test
    public void getUserPostWithoutToken() {
        Response responseAfterGetPost = given()
                .queryParam("page", correctPage)
                .queryParam("perPage", correctPerPage)
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType())
                .extract().response();

        String success = responseAfterGetPost.jsonPath().getString("success");
        int customStatusCode = responseAfterGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterGetPost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.UNAUTHORIZED), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Get user news")
    @Story("With page = 0")
    @Description(value = "Checking the correct server response")
    @Test
    public void getUserPostWithPageEqual0() {
        Response response = given()
                .header("Authorization", token)
                .queryParam("page", 0)
                .queryParam("perPage", correctPage)
                .when()
                .get(Routes.news + "/user/" + userId)
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
    @Feature("Get user news")
    @Story("With perPage = 0")
    @Description(value = "Checking the correct server response")
    @Test
    public void getUserPostWithPerPageEqual0() {
        Response response = given()
                .header("Authorization", token)
                .queryParam("page", correctPage)
                .queryParam("perPage", 0)
                .when()
                .get(Routes.news + "/user/" + userId)
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
