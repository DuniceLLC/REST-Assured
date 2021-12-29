import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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

    @Epic("News-controller")
    @Feature("Delete news")
    @Story("Correct request")
    @Description(value = "Checking news deletion")
    @Test
    public void deletePostTest() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");
        Response responseAfterDeletePost = Methods.deletePost(token, postId);
        String success = responseAfterDeletePost.jsonPath().getString("success");
        int customStatusCode = responseAfterDeletePost.jsonPath().getInt("statusCode");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertEquals(customStatusCode,1,"Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
