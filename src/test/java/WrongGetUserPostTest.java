import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class WrongGetUserPostTest extends GetPostTest {
    int correctPerPage = 7;
    int correctPage = 1;
    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode = new ErrorCode();

    @Test
    public void getUserPostWithoutPage() {
        Response responseAfterGetPost = given()
                .header("Authorization", token)
                .queryParam("perPage", correctPerPage)
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseAfterGetPost.jsonPath().getString("success");
        int customStatusCode = responseAfterGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterGetPost.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void getUserPostWithoutPerPage() {
        Response responseAfterGetPost = given()
                .header("Authorization", token)
                .queryParam("page", correctPage)
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();

        String success = responseAfterGetPost.jsonPath().getString("success");
        int customStatusCode = responseAfterGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterGetPost.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void getUserPostWithoutToken() {
        Response responseAfterGetPost = given()
                .queryParam("page", correctPage)
                .queryParam("perPage", correctPerPage)
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType())
                .extract().response();

        String success = responseAfterGetPost.jsonPath().getString("success");
        int customStatusCode = responseAfterGetPost.jsonPath().getInt("statusCode");
        List<Integer> codes = responseAfterGetPost.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.UNAUTHORIZED);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void getUserPostWithPageEqual0() {
        Response response = given()
                .header("Authorization", token)
                .queryParam("page", 0)
                .queryParam("perPage", correctPage)
                .when()
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.PARAM_PAGE_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void getUserPostWithPerPageEqual0() {
        Response response = given()
                .header("Authorization", token)
                .queryParam("page", correctPage)
                .queryParam("perPage", 0)
                .when()
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.PARAM_PER_PAGE_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }
}
