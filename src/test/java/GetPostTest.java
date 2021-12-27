import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class GetPostTest extends SetUp {
    SoftAssert softAssert = new SoftAssert();
    String description = Methods.generateRandomHexString(5);
    String imagePath = "src/main/resources/postPicture.jpeg";
    String image = Methods.uploadFile(imagePath).jsonPath().getString("data");
    String[] tags = {Methods.generateRandomHexString(5)};
    String title = Methods.generateRandomHexString(5);
    Post newsDto = new Post(description, image, tags, title);
    String author;
    String userId;
    String userName;

    @BeforeMethod
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

        softAssert.assertNotNull(id);
        softAssert.assertEquals(titleGetPost, title);
        softAssert.assertEquals(userIdGetPost, userId);
        softAssert.assertEquals(userNameGetPost, userName);
        softAssert.assertEquals(imageGetPost, image);
        softAssert.assertEquals(descriptionGetPost, description);
        softAssert.assertNotNull(tagsIdGetPost);
        softAssert.assertEquals(tagsTitleGetPost, this.tags[0]);
        softAssert.assertAll();
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

        softAssert.assertEquals(description, this.description);
        softAssert.assertNotNull(id);
        softAssert.assertEquals(image, this.image);
        softAssert.assertEquals(tagsTitle, this.tags[0]);
        softAssert.assertNotNull(tagsId);
        softAssert.assertEquals(title, this.title);
        softAssert.assertEquals(userId, this.userId);
        softAssert.assertEquals(userName, this.userName);
        softAssert.assertNotNull(numberOfElements);
        softAssert.assertEquals(customStatusCode, 1);
        softAssert.assertEquals(success, "true");
        softAssert.assertAll();
    }
}
