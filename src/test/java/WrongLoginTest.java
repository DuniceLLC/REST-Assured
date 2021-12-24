import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.Test;
import java.util.List;

public class WrongLoginTest {
    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode = new ErrorCode();
    JSONObject requestBody = new JSONObject();
    RequestSpecification request = RestAssured.given();

    String wrongEmail1 = Methods.generateRandomHexString(5);
    String wrongEmail2 = Methods.generateRandomHexString(5) + "@";
    String wrongEmail3 = Methods.generateRandomHexString(5) + "@email";
    String wrongEmail4 = Methods.generateRandomHexString(5) + "@email.";
    String wrongEmail5 = Methods.generateRandomHexString(5) + "@email.c";
    String correctEmail = Methods.generateRandomHexString(5) + "@email.com";
    String emptyEmail = "";
    String emptyPassword = "";
    String password = Methods.generateRandomHexString(6);

    @Test
    public void incorrectEmail1() {
        requestBody.put("email", wrongEmail1);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then()
                .assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_VALID);
        softAssertions.assertAll();
    }

    @Test
    public void incorrectEmail2() {
        requestBody.put("email", wrongEmail2);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then()
                .assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_VALID);
        softAssertions.assertAll();
    }

    @Test
    public void incorrectEmail3() {
        requestBody.put("email", wrongEmail3);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then()
                .assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_VALID);
        softAssertions.assertAll();
    }

    @Test
    public void incorrectEmail4() {
        requestBody.put("email", wrongEmail4);
        requestBody.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_VALID);

        softAssertions.assertAll();
    }

    @Test
    public void incorrectEmail5() {
        requestBody.put("email", wrongEmail5);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_VALID);
        softAssertions.assertAll();
    }

    @Test
    public void userDoesNotExist() {
        requestBody.put("email", correctEmail);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_NOT_FOUND);
        softAssertions.assertAll();
    }

    @Test
    public void emptyEmail() {
        requestBody.put("email", emptyEmail);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_NULL);
        softAssertions.assertAll();
    }

    @Test
    public void emptyPassword() {
        requestBody.put("email", correctEmail);
        requestBody.put("password", emptyPassword);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_PASSWORD_NULL);
        softAssertions.assertAll();
    }

    @Test
    public void emptyEmailAndPassword() {
        requestBody.put("email", emptyEmail);
        requestBody.put("password", emptyPassword);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_NULL, errorCode.USER_PASSWORD_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void correctEmailWithoutPassword() {
        requestBody.put("email", correctEmail);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(codes).contains(errorCode.PASSWORD_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void WithoutEmail() {
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withoutEmailAndPassword() {
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_NULL, errorCode.PASSWORD_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }
}
