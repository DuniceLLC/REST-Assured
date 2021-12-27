import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DeletePostTest extends SetUp {
    String imagePath = "src/main/resources/postPicture.jpeg";
    String image = Methods.uploadFile(imagePath).jsonPath().getString("data");

    String description = Methods.generateRandomHexString(5);
    String[] tags = {Methods.generateRandomHexString(5)};
    String title = Methods.generateRandomHexString(5);
    Post newsDto = new Post(description, image, tags, title);

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void deletePostTest() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");
        Response responseAfterDeletePost = Methods.deletePost(token, postId);
        String success = responseAfterDeletePost.jsonPath().getString("success");
        int customStatusCode = responseAfterDeletePost.jsonPath().getInt("statusCode");

        softAssert.assertEquals(success,"true");
        softAssert.assertEquals(customStatusCode,1);
        softAssert.assertAll();
    }
}
