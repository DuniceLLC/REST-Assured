import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.*;

public class WrongCreatePostTest extends SetUp {
    String imagePath = "src/main/resources/postPicture.jpeg";
    Response imageData = Methods.uploadFile(imagePath);
    String image = imageData.jsonPath().getString("data");
    String description = Methods.generateRandomHexString(5);
    String[] tags = {Methods.generateRandomHexString(5)};
    String title = Methods.generateRandomHexString(5);

    String emptyField = "";

    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode = new ErrorCode();

    @Test
    public void createPostWithoutToken() {
        Post newsDto = new Post(description, image, tags, title);
        Response responseAfterCreatePost = given()
                .body(newsDto)
                .when()
                .post(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType()).extract().response();

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.UNAUTHORIZED, errorCode.TOKEN_NOT_PROVIDED);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void createPostWithEmptyDescription() {
        Post newsDto = new Post(emptyField, image, tags, title);
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.NEWS_DESCRIPTION_SIZE);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void createPostWithEmptyTitle() {
        Post newsDto = new Post(description, image, tags, emptyField);
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.NEWS_TITLE_SIZE);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void createPostWithEmptyImage() {
        Post newsDto = new Post(description, emptyField, tags, title);
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.NEWS_IMAGE_HAS_TO_BE_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void createPostWithoutDescription() {
        Post newsDto = Post.builder().image(image).title(title).tags(tags).build();
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.NEWS_DESCRIPTION_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void createPostWithoutImage() {
        Post newsDto = Post.builder().description(description).title(title).tags(tags).build();
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.NEWS_IMAGE_HAS_TO_BE_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void createPostWithoutTitle() {
        Post newsDto = Post.builder().description(description).image(image).tags(tags).build();
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.NEWS_TITLE_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }
}
