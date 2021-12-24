import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class CreatePostTest extends SetUp {
    SoftAssertions softAssertions = new SoftAssertions();

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

        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertAll();
    }
}
