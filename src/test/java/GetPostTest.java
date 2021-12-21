import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class GetPostTest extends SetUp {
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void getPostTest() {
        String author = response.jsonPath().getString("data.name");
        String userId = response.jsonPath().getString("data.id");
        String userName = response.jsonPath().getString("data.name");
        String description = Methods.generateRandomHexString(5);
        String image = "src/main/resources/postPicture.jpeg";
        String[] tags = {Methods.generateRandomHexString(5)};
        String title = Methods.generateRandomHexString(5);
        Post newsDto = new Post(description, image, tags, title);
        Response responseCreatePost = Methods.createPost(token, newsDto);
        responseCreatePost.prettyPrint();

        Response responseGetPost = Methods.getPost(author, description, 1, 1, tags);
        responseGetPost.prettyPrint();

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

    }
}
