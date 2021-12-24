import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class WrongGetSpecificPostTest extends GetPostTest {
    int correctPerPage = 7;
    int correctPage = 1;
    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode = new ErrorCode();

    @Test
    public void getPostWithoutPageTest() {
        Response responseGetPost = given()
                .queryParam("author", author)
                .queryParam("keywords", description)
                .queryParam("perPage", correctPerPage)
                .queryParam("tags", tags)
                .get(Routes.news + "/find")
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseGetPost.jsonPath().getString("success");
        int customStatusCode = responseGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseGetPost.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void getPostWithoutPerPageTest() {
        Response responseGetPost = given()
                .queryParam("author", author)
                .queryParam("keywords", description)
                .queryParam("page", correctPage)
                .queryParam("tags", tags)
                .get(Routes.news + "/find")
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseGetPost.jsonPath().getString("success");
        int customStatusCode = responseGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseGetPost.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();

        softAssertions.assertAll();
    }
}
