import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

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
        Response responseAfterCreatePost = given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(newsDto)
                .when()
                .post(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();

        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        String success = responseAfterCreatePost.jsonPath().getString("success");

        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertAll();
    }
}
