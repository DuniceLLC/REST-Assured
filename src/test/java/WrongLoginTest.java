import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.Test;
import java.util.List;

public class WrongLoginTest {

    SoftAssertions softAssertions = new SoftAssertions();
    Routes routes = new Routes();
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
              .contentType(ContentType.JSON).extract().response();

      Methods.showBodyPostLogin(request, routes);

      int statusCode = response.getStatusCode();
      String success = response.jsonPath().getString("success");
      int customStatusCode = response.jsonPath().getInt("statusCode");
      List<String> codes = response.jsonPath().getList("codes");

      softAssertions.assertThat(400).isEqualTo(statusCode);
      softAssertions.assertThat("true").isEqualTo(success);
      softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
      softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

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
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

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
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

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
                .then()
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

        softAssertions.assertAll();
    }

    @Test
    public void incorrectEmail5() {
        requestBody.put("email", wrongEmail5);
        requestBody.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request
                .post(routes.login)
                .then()
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes);

        softAssertions.assertAll();
    }

    @Test
    public void userDoesNotExist() {
        requestBody.put("email", correctEmail);
        requestBody.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request
                .post(routes.login)
                .then()
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_NOT_FOUND).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_NOT_FOUND).isEqualTo(codes.get(0));

        softAssertions.assertAll();
    }

    @Test
    public void emptyEmail() {
        requestBody.put("email", emptyEmail);
        requestBody.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request
                .post(routes.login)
                .then()
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_NULL).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_NULL).isEqualTo(codes);

        softAssertions.assertAll();
    }

    @Test
    public void emptyPassword() {
        requestBody.put("email", correctEmail);
        requestBody.put("password", emptyPassword);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request
                .post(routes.login)
                .then()
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_PASSWORD_NULL).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_PASSWORD_NULL).isEqualTo(codes);

        softAssertions.assertAll();
    }

    @Test
    public void emptyEmailAndPassword() {
        requestBody.put("email", emptyEmail);
        requestBody.put("password", emptyPassword);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        Response response = request
                .post(routes.login)
                .then()
                .contentType(ContentType.JSON).extract().response();

        Methods.showBodyPostLogin(request, routes);

        int statusCode = response.getStatusCode();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<String> codes = response.jsonPath().getList("codes");

        softAssertions.assertThat(400).isEqualTo(statusCode);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_NULL).isEqualTo(customStatusCode);
        softAssertions.assertThat(errorCode.USER_EMAIL_NOT_NULL).isEqualTo(codes.get(0));
        softAssertions.assertThat(errorCode.USER_PASSWORD_NULL).isEqualTo(codes.get(1));

        softAssertions.assertAll();
    }

        @Test
        public void correctEmailWithoutPassword() {
            requestBody.put("email", wrongEmail1);

            request.header("Content-Type", "application/json");
            request.body(requestBody.toString());

            Response response = request
                    .post(routes.login)
                    .then()
                    .contentType(ContentType.JSON).extract().response();

            Methods.showBodyPostLogin(request, routes);

            int statusCode = response.getStatusCode();
            String success = response.jsonPath().getString("success");
            int customStatusCode = response.jsonPath().getInt("statusCode");
            List<String> codes = response.jsonPath().getList("codes");

            softAssertions.assertThat(400).isEqualTo(statusCode);
            softAssertions.assertThat("true").isEqualTo(success);
            softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(customStatusCode);
            softAssertions.assertThat(errorCode.PASSWORD_NOT_NULL).isEqualTo(codes.get(0));
            softAssertions.assertThat(errorCode.USER_EMAIL_NOT_VALID).isEqualTo(codes.get(1));

            softAssertions.assertAll();
    }
}
