import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.*;
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

    ErrorCode errorCode = new ErrorCode();

    @Epic("News-controller")
    @Feature("Create news")
    @Story("Without token")
    @Description(value = "Checking the correct server response")
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

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.UNAUTHORIZED), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.TOKEN_NOT_PROVIDED), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Create news")
    @Story("With empty description")
    @Description(value = "Checking the correct server response")
    @Test
    public void createPostWithEmptyDescription() {
        Post newsDto = new Post(emptyField, image, tags, title);
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_DESCRIPTION_SIZE), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Create news")
    @Story("With empty title")
    @Description(value = "Checking the correct server response")
    @Test
    public void createPostWithEmptyTitle() {
        Post newsDto = new Post(description, image, tags, emptyField);
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_TITLE_SIZE), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Create news")
    @Story("With empty image")
    @Description(value = "Checking the correct server response")
    @Test
    public void createPostWithEmptyImage() {
        Post newsDto = new Post(description, emptyField, tags, title);
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_IMAGE_HAS_TO_BE_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Create news")
    @Story("Without description")
    @Description(value = "Checking the correct server response")
    @Test
    public void createPostWithoutDescription() {
        Post newsDto = Post.builder().image(image).title(title).tags(tags).build();
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_DESCRIPTION_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Create news")
    @Story("Without image")
    @Description(value = "Checking the correct server response")
    @Test
    public void createPostWithoutImage() {
        Post newsDto = Post.builder().description(description).title(title).tags(tags).build();
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_IMAGE_HAS_TO_BE_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("News-controller")
    @Feature("Create news")
    @Story("Without title")
    @Description(value = "Checking the correct server response")
    @Test
    public void createPostWithoutTitle() {
        Post newsDto = Post.builder().description(description).image(image).tags(tags).build();
        Response responseAfterCreatePost = Methods.wrongCreatePost(token, newsDto);

        String success = responseAfterCreatePost.jsonPath().getString("success");
        int customStatusCode = responseAfterCreatePost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterCreatePost.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.NEWS_TITLE_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
