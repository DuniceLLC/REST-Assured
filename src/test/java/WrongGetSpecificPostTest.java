import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import static io.restassured.RestAssured.given;

public class WrongGetSpecificPostTest extends GetPostTest {
    int correctPerPage = 7;
    int correctPage = 1;
    SoftAssert softAssert = new SoftAssert();
    ErrorCode errorCode = new ErrorCode();

    @Epic("News-controller")
    @Feature("Get specific news")
    @Story("Without page")
    @Description(value = "Checking the correct server response")
    @Test
    public void getPostWithoutPageTest() {
        Response responseGetPost = given()
                .queryParam("author", author)
                .queryParam("keywords", description)
                .queryParam("perPage", correctPerPage)
                .queryParam("tags", tags)
                .get(routes.getNews() + "/find")
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseGetPost.jsonPath().getString("success");
        int customStatusCode = responseGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseGetPost.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Get specific news")
    @Story("Without perPage")
    @Description(value = "Checking the correct server response")
    @Test
    public void getPostWithoutPerPageTest() {
        Response responseGetPost = given()
                .queryParam("author", author)
                .queryParam("keywords", description)
                .queryParam("page", correctPage)
                .queryParam("tags", tags)
                .get(routes.getNews() + "/find")
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseGetPost.jsonPath().getString("success");
        int customStatusCode = responseGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseGetPost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
