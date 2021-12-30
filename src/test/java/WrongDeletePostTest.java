import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import static io.restassured.RestAssured.given;

public class WrongDeletePostTest extends SetUp {
    String imagePath = "src/main/resources/postPicture.jpeg";
    String image = methods.uploadFile(imagePath).jsonPath().getString("data");
    int wrongId = 0;

    String description = Methods.generateRandomHexString(5);
    String[] tags = {Methods.generateRandomHexString(5)};
    String title = Methods.generateRandomHexString(5);
    Post newsDto = new Post(description, image, tags, title);

    ErrorCode errorCode = new ErrorCode();

    @Epic("News-controller")
    @Feature("Delete news")
    @Story("With wrong ID")
    @Description(value = "Checking the correct server response")
    @Test
    public void deletePostTestWithWrongId() {
        methods.createPost(token, newsDto);
        Response responseAfterDeletePost = given()
                .header("Authorization", token)
                .when().delete(routes.getNews() + "/" + wrongId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseAfterDeletePost.jsonPath().getString("success");
        int customStatusCode = responseAfterDeletePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterDeletePost.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_NOT_FOUND), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Delete news")
    @Story("Without token")
    @Description(value = "Checking the correct server response")
    @Test
    public void deletePostTestWithoutToken() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");
        Response responseAfterDeletePost = given()
                .when().delete(routes.getNews() + "/" + postId)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType())
                .extract().response();

        String success = responseAfterDeletePost.jsonPath().getString("success");
        int customStatusCode = responseAfterDeletePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterDeletePost.jsonPath().getList("codes");
        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.UNAUTHORIZED), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
