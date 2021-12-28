import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import static io.restassured.RestAssured.given;

public class WrongDeletePostTest extends SetUp {
    String imagePath = "src/main/resources/postPicture.jpeg";
    String image = Methods.uploadFile(imagePath).jsonPath().getString("data");
    int wrongId = 0;

    String description = Methods.generateRandomHexString(5);
    String[] tags = {Methods.generateRandomHexString(5)};
    String title = Methods.generateRandomHexString(5);
    Post newsDto = new Post(description, image, tags, title);

    SoftAssert softAssert = new SoftAssert();
    ErrorCode errorCode = new ErrorCode();

    @Test
    public void deletePostTestWithWrongId() {
        Methods.createPost(token, newsDto);
        Response responseAfterDeletePost = given()
                .header("Authorization", token)
                .when().delete(Routes.news + "/" + wrongId)
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

    @Test
    public void deletePostTestWithoutToken() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");
        Response responseAfterDeletePost = given()
                .when().delete(Routes.news + "/" + postId)
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
