import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetPostTest extends SetUp {
    SoftAssertions softAssertions = new SoftAssertions();
    String description = Methods.generateRandomHexString(5);
    String imagePath = "src/main/resources/postPicture.jpeg";
    String image = Methods.uploadFile(imagePath).jsonPath().getString("data");
    String[] tags = {Methods.generateRandomHexString(5)};
    String title = Methods.generateRandomHexString(5);
    Post newsDto = new Post(description, image, tags, title);
    String author;
    String userId;
    String userName;

    @BeforeEach
    public void createPost() {
        author = response.jsonPath().getString("data.name");
        userId = response.jsonPath().getString("data.id");
        userName = response.jsonPath().getString("data.name");
        Methods.createPost(token, newsDto);
    }

    @Test
    public void getPostTest() {
        Response responseGetPost = Methods.getPost(author, description, 1, 1, tags);

        int id = responseGetPost.jsonPath().getInt("content[0].id");
        String titleGetPost = responseGetPost.jsonPath().getString("content[0].title");
        String userIdGetPost = responseGetPost.jsonPath().getString("content[0].userId");
        String userNameGetPost = responseGetPost.jsonPath().getString("content[0].username");
        String imageGetPost = responseGetPost.jsonPath().getString("content[0].image");
        String descriptionGetPost = responseGetPost.jsonPath().getString("content[0].description");
        String tagsIdGetPost = responseGetPost.jsonPath().getString("content[0].tags[0].id");
        String tagsTitleGetPost = responseGetPost.jsonPath().getString("content[0].tags[0].title");

        softAssertions.assertThat(id).isNotNull();
        softAssertions.assertThat(titleGetPost).isEqualTo(title);
        softAssertions.assertThat(userIdGetPost).isEqualTo(userId);
        softAssertions.assertThat(userNameGetPost).isEqualTo(userName);
        softAssertions.assertThat(imageGetPost).isEqualTo(image);
        softAssertions.assertThat(descriptionGetPost).isEqualTo(description);
        softAssertions.assertThat(tagsIdGetPost).isNotNull();
        softAssertions.assertThat(tagsTitleGetPost).contains(tags);
        softAssertions.assertAll();
    }

    @Test
    public void getUserPostTest() {
        Response responseGetUserPost = Methods.getUserPost(1, 1, userId, token);

        String description = responseGetUserPost.jsonPath().getString("data.content[0].description");
        int id = responseGetUserPost.jsonPath().getInt("data.content[0].id");
        String image = responseGetUserPost.jsonPath().getString("data.content[0].image");
        String tagsTitle = responseGetUserPost.jsonPath().getString("data.content[0].tags[0].title");
        int tagsId = responseGetUserPost.jsonPath().getInt("data.content[0].tags[0].id");
        String title = responseGetUserPost.jsonPath().getString("data.content[0].title");
        String userId = responseGetUserPost.jsonPath().getString("data.content[0].userId");
        String userName = responseGetUserPost.jsonPath().getString("data.content[0].username");
        int numberOfElements = responseGetUserPost.jsonPath().getInt("data.numberOfElements");
        int customStatusCode = responseGetUserPost.jsonPath().getInt("statusCode");
        String success = responseGetUserPost.jsonPath().getString("success");

        softAssertions.assertThat(description).isEqualTo(this.description);
        softAssertions.assertThat(id).isNotNull();
        softAssertions.assertThat(image).isEqualTo(this.image);
        softAssertions.assertThat(tagsTitle).isEqualTo(this.tags[0]);
        softAssertions.assertThat(tagsId).isNotNull();
        softAssertions.assertThat(title).isEqualTo(this.title);
        softAssertions.assertThat(userId).isEqualTo(this.userId);
        softAssertions.assertThat(userName).isEqualTo(this.userName);
        softAssertions.assertThat(numberOfElements).isNotNull();
        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertAll();
    }
}
