import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ChangePostTest extends SetUp {

    String imagePath = "src/main/resources/postPicture.jpeg";
    String imagePathForChange = "src/main/resources/postPicture-2.jpeg";
    String image1 = Methods.uploadFile(imagePath).jsonPath().getString("data");

    String description = Methods.generateRandomHexString(5);
    String[] tags = {Methods.generateRandomHexString(5)};
    String title = Methods.generateRandomHexString(5);
    Post newsDto = new Post(description, image1, tags, title);

    SoftAssert softAssert = new SoftAssert();

    @Epic("News-controller")
    @Feature("Change news")
    @Story("Correct request")
    @Description(value = "Checking change news")
    @Test
    public void changePostTest() {
        Methods.createPost(token, newsDto);
        String image2 = Methods.uploadFile(imagePathForChange).jsonPath().getString("data");
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");
        String newDescription = Methods.generateRandomHexString(5);
        String[] newTags = {Methods.generateRandomHexString(5)};
        String mewTitle = Methods.generateRandomHexString(5);
        Post newDtoForChange = new Post(newDescription, image2, newTags, mewTitle);
        Response responseAfterChangePost = Methods.changePost(token, postId, newDtoForChange);

        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        String success = responseAfterChangePost.jsonPath().getString("success");

        softAssert.assertEquals(customStatusCode, 1, "Wrong \"statusCode\"");
        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertAll();
    }
}
