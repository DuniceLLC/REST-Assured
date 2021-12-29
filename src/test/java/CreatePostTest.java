import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreatePostTest extends SetUp {
    SoftAssert softAssert = new SoftAssert();

    @Epic("News-controller")
    @Feature("Create news")
    @Story("Correct request")
    @Description(value = "Checking news creation")
    @Test
    public void createPostTest() {
        String imagePath = "src/main/resources/postPicture.jpeg";
        Response imageData = Methods.uploadFile(imagePath);
        String image = imageData.jsonPath().getString("data");
        String description = Methods.generateRandomHexString(5);
        String[] tags = {Methods.generateRandomHexString(5)};
        String title = Methods.generateRandomHexString(5);
        Post newsDto = new Post(description, image, tags, title);
        Response responseAfterCreatePost = Methods.createPost(token, newsDto);

        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        String success = responseAfterCreatePost.jsonPath().getString("success");

        softAssert.assertEquals(customStatusCode, 1,"Wrong \"statusCode\"");
        softAssert.assertEquals(success, "true","Wrong \"success\"");
        softAssert.assertAll();
    }
}
