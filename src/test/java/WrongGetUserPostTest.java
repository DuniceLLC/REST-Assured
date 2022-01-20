import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class WrongGetUserPostTest extends GetPostTest {
    Routes routes = new Routes();
    int correctPerPage = 7;
    int correctPage = 1;
    ErrorCode errorCode = new ErrorCode();




    @Epic("News-controller")
    @Feature("Get user news")
    @Story("Without token")
    @Description(value = "Checking the correct server response")
    @Test
    public void getUserPostWithoutToken() {
        Response responseAfterGetPost = given()
                .queryParam("page", correctPage)
                .queryParam("perPage", correctPerPage)
                .get(routes.getNews() + "/user/" + userId)
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



}
