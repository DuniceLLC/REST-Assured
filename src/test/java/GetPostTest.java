import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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

    @Epic("News-controller")
    @Feature("Get news")
    @Story("Correct request")
    @Description(value = "Get news check")
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

        softAssert.assertNotNull(id, "id is null");
        softAssert.assertEquals(titleGetPost, title,"Wrong \"title\"");
        softAssert.assertEquals(userIdGetPost, userId,"Wrong \"userId\"");
        softAssert.assertEquals(userNameGetPost, userName,"Wrong \"username\"");
        softAssert.assertEquals(imageGetPost, image,"Wrong \"image\"");
        softAssert.assertEquals(descriptionGetPost, description,"Wrong \"description\"");
        softAssert.assertNotNull(tagsIdGetPost, "tags id is null");
        softAssert.assertEquals(tagsTitleGetPost, this.tags[0], "Wrong tags title");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Get user news")
    @Story("Correct request")
    @Description(value = "Get user news check")
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

        softAssert.assertEquals(description, this.description,"Wrong \"description\"");
        softAssert.assertNotNull(id, "id is null");
        softAssert.assertEquals(image, this.image,"Wrong \"image\"");
        softAssert.assertEquals(tagsTitle, this.tags[0], "Wrong tags title");
        softAssert.assertNotNull(tagsId, "tags id is null");
        softAssert.assertEquals(title, this.title,"Wrong \"title\"");
        softAssert.assertEquals(userId, this.userId,"Wrong \"userId\"");
        softAssert.assertEquals(userName, this.userName,"Wrong \"username\"");
        softAssert.assertNotNull(numberOfElements, "\"numberOfElements\" is null");
        softAssert.assertEquals(customStatusCode, 1, "Wrong \"statusCode\"");
        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertAll();
    }
}
