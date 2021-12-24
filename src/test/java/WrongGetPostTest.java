import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class WrongGetPostTest {
    int correctPerPage = 7;
    int correctPage = 1;

    ErrorCode errorCode = new ErrorCode();
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void getPostWithoutPage() {
        Response response = given()
                .queryParam("perPage", correctPerPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void getPostWithoutPerPage() {
        Response response = given()
                .queryParam("page", correctPage)
                .when()
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void getPostWithPageEqual0() {
        Response response = given()
                .queryParam("page", 0)
                .queryParam("perPage", correctPage)
                .when()
                .get(Routes.news)
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
    public void getPostWithPerPageEqual0() {
        Response response = given()
                .queryParam("page", correctPage)
                .queryParam("perPage", 0)
                .when()
                .get(Routes.news)
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
