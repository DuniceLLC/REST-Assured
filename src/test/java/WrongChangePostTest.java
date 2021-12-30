import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import static io.restassured.RestAssured.given;

public class WrongChangePostTest extends SetUp {
    ErrorCode errorCode = new ErrorCode();
    String emptyField = "";
    String[] emptyTags = {emptyField};
    int wrongId = 0;
    String imagePath = "src/main/resources/postPicture.jpeg";
    String imagePathForChange = "src/main/resources/postPicture-2.jpeg";
    String image1 = methods.uploadFile(imagePath).jsonPath().getString("data");
    String image2 = methods.uploadFile(imagePathForChange).jsonPath().getString("data");

    String newDescription = Methods.generateRandomHexString(5);
    String[] newTags = {Methods.generateRandomHexString(5)};
    String newTitle = Methods.generateRandomHexString(5);

    String description = Methods.generateRandomHexString(6);
    String[] tags = {Methods.generateRandomHexString(6)};
    String title = Methods.generateRandomHexString(6);
    Post newsDto = new Post(description, image1, tags, title);

    @Epic("News-controller")
    @Feature("Change news")
    @Story("Without description")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithoutDescription() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = Post.builder().image(image2).tags(newTags).title(newTitle).build();
        Response responseAfterChangePost = methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_DESCRIPTION_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Change news")
    @Story("Without image")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithoutImage() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = Post.builder().description(newDescription).tags(newTags).title(newTitle).build();
        Response responseAfterChangePost = methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_IMAGE_HAS_TO_BE_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Change news")
    @Story("Without title")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithoutTitle() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = Post.builder().description(newDescription).tags(newTags).image(image2).build();
        Response responseAfterChangePost = methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_TITLE_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Change news")
    @Story("Without token")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithoutToken() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        String postId = responseAfterGetPost.jsonPath().getString("content[0].id");

        Post newDtoForChange = new Post(newDescription, image2, newTags, newTitle);
        Response responseAfterChangePost = given()
                .spec(Specifications.setContentType())
                .body(newDtoForChange)
                .when().put(routes.getNews() + "/" + postId)
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

    @Epic("News-controller")
    @Feature("Change news")
    @Story("With wrong ID")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithWrongId() {
        methods.createPost(token, newsDto);

        Post newDtoForChange = new Post(newDescription, image2, newTags, newTitle);
        Response responseAfterChangePost = methods.wrongChangePost(token, wrongId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_NOT_FOUND), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Change news")
    @Story("With empty description")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithEmptyDescription() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = new Post(emptyField, image2, newTags, newTitle);
        Response responseAfterChangePost = methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_DESCRIPTION_SIZE), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Change news")
    @Story("With empty title")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithEmptyTitle() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = new Post(newDescription, image2, newTags, emptyField);
        Response responseAfterChangePost = methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_TITLE_SIZE), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Change news")
    @Story("With empty tags")
    @Description(value = "Checking the correct server response")
    @Test
    public void wrongChangePostTestWithEmptyTags() {
        methods.createPost(token, newsDto);
        String author = response.jsonPath().getString("data.name");
        Response responseAfterGetPost = methods.getPost(author, description, 1, 1, tags);
        int postId = responseAfterGetPost.jsonPath().getInt("content[0].id");

        Post newDtoForChange = new Post(newDescription, image2, emptyTags, newTitle);
        Response responseAfterChangePost = methods.wrongChangePost(token, postId, newDtoForChange);

        String success = responseAfterChangePost.jsonPath().getString("success");
        int customStatusCode = responseAfterChangePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterChangePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.TAGS_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
