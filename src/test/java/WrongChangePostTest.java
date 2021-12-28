import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import static io.restassured.RestAssured.given;

public class WrongChangePostTest extends SetUp {
    ErrorCode errorCode = new ErrorCode();
    SoftAssert softAssert = new SoftAssert();
    String emptyField = "";
    String[] emptyTags = {emptyField};
    int wrongId = 0;
    String imagePath = "src/main/resources/postPicture.jpeg";
    String imagePathForChange = "src/main/resources/postPicture-2.jpeg";
    String image1 = Methods.uploadFile(imagePath).jsonPath().getString("data");
    String image2 = Methods.uploadFile(imagePathForChange).jsonPath().getString("data");

    String newDescription = Methods.generateRandomHexString(5);
    String[] newTags = {Methods.generateRandomHexString(5)};
    String newTitle = Methods.generateRandomHexString(5);

    String description = Methods.generateRandomHexString(6);
    String[] tags = {Methods.generateRandomHexString(6)};
    String title = Methods.generateRandomHexString(6);
    Post newsDto = new Post(description, image1, tags, title);

    @Test
    public void wrongChangePostTestWithoutDescription() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = Post.builder().image(image2).tags(newTags).title(newTitle).build();
        Response responseAfterChangePost = Methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_DESCRIPTION_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void wrongChangePostTestWithoutImage() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = Post.builder().description(newDescription).tags(newTags).title(newTitle).build();
        Response responseAfterChangePost = Methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_IMAGE_HAS_TO_BE_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void wrongChangePostTestWithoutTitle() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = Post.builder().description(newDescription).tags(newTags).image(image2).build();
        Response responseAfterChangePost = Methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_TITLE_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void wrongChangePostTestWithoutToken() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        String postId = responseAfterGetPost.jsonPath().getString("content[0].id");

        Post newDtoForChange = new Post(newDescription, image2, newTags, newTitle);
        Response responseAfterChangePost = given()
                .spec(Specifications.setContentType())
                .body(newDtoForChange)
                .when().put(Routes.news + "/" + postId)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType())
                .extract().response();

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.UNAUTHORIZED), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void wrongChangePostTestWithWrongId() {
        Methods.createPost(token, newsDto);

        Post newDtoForChange = new Post(newDescription, image2, newTags, newTitle);
        Response responseAfterChangePost = Methods.wrongChangePost(token, wrongId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_NOT_FOUND), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void wrongChangePostTestWithEmptyDescription() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = new Post(emptyField, image2, newTags, newTitle);
        Response responseAfterChangePost = Methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_DESCRIPTION_SIZE), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void wrongChangePostTestWithEmptyTitle() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = new Post(newDescription, image2, newTags, emptyField);
        Response responseAfterChangePost = Methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_TITLE_SIZE), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void wrongChangePostTestWithEmptyTags() {
        Methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = Methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = new Post(newDescription, image2, emptyTags, newTitle);
        Response responseAfterChangePost = Methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.TAGS_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
