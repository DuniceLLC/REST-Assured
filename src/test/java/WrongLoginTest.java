import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.util.List;

public class WrongLoginTest extends SetUp {
    ErrorCode errorCode = new ErrorCode();
    JSONObject requestBody = new JSONObject();
    RequestSpecification request = RestAssured.given();

    String wrongEmail1 = methods.generateRandomHexString(5);
    String wrongEmail2 = methods.generateRandomHexString(5) + "@";
    String wrongEmail3 = methods.generateRandomHexString(5) + "@email";
    String wrongEmail4 = methods.generateRandomHexString(5) + "@email.";
    String wrongEmail5 = methods.generateRandomHexString(5) + "@email.c";
    String emptyEmail = "";
    String emptyPassword = "";
    String password = methods.generateRandomHexString(6);
    String randomEmail = methods.generateRandomHexString(5) + "@gmail.com";

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With wrong email")
    @Description(value = "Checking the correct server response")
    @Test
    public void incorrectEmail1() {
        requestBody.put("email", wrongEmail1);
        requestBody.put("password", correctPassword);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertFalse(codes.contains(errorCode.USER_EMAIL_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With wrong email")
    @Description(value = "Checking the correct server response")
    @Test
    public void incorrectEmail2() {
        requestBody.put("email", wrongEmail2);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertFalse(codes.contains(errorCode.USER_EMAIL_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With wrong email")
    @Description(value = "Checking the correct server response")
    @Test
    public void incorrectEmail3() {
        requestBody.put("email", wrongEmail3);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertFalse(codes.contains(errorCode.USER_EMAIL_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With wrong email")
    @Description(value = "Checking the correct server response")
    @Test
    public void incorrectEmail4() {
        requestBody.put("email", wrongEmail4);
        requestBody.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertFalse(codes.contains(errorCode.USER_EMAIL_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With wrong email")
    @Description(value = "Checking the correct server response")
    @Test
    public void incorrectEmail5() {
        requestBody.put("email", wrongEmail5);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertFalse(codes.contains(errorCode.USER_EMAIL_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With non-existent email")
    @Description(value = "Checking the correct server response")
    @Test
    public void userDoesNotExist() {
        requestBody.put("email", randomEmail);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_NOT_FOUND), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With empty email")
    @Description(value = "Checking the correct server response")
    @Test
    public void emptyEmail() {
        requestBody.put("email", emptyEmail);
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.EMAIL_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With empty password")
    @Description(value = "Checking the correct server response")
    @Test
    public void emptyPassword() {
        requestBody.put("email", correctEmail);
        requestBody.put("password", emptyPassword);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.PASSWORD_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With empty email and empty password")
    @Description(value = "Checking the correct server response")
    @Test
    public void emptyEmailAndPassword() {
        requestBody.put("email", emptyEmail);
        requestBody.put("password", emptyPassword);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.EMAIL_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertFalse(codes.contains(errorCode.PASSWORD_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("With correct email and without password")
    @Description(value = "Checking the correct server response")
    @Test
    public void correctEmailWithoutPassword() {
        requestBody.put("email", correctEmail);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.PASSWORD_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("Without email")
    @Description(value = "Checking the correct server response")
    @Test
    public void withoutEmail() {
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertFalse(codes.contains(errorCode.USER_EMAIL_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("Without email and without password")
    @Description(value = "Checking the correct server response")
    @Test
    public void withoutEmailAndPassword() {
        requestBody.put("password", password);
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType()).extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertFalse(codes.contains(errorCode.USER_EMAIL_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertFalse(codes.contains(errorCode.PASSWORD_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
