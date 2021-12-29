import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static io.restassured.RestAssured.given;

public class WrongChangeUserInfoTest extends SetUp {

    ErrorCode errorCode = new ErrorCode();

    String wrongEmail = Methods.generateRandomHexString(6);
    String wrongName = Methods.generateRandomHexString(2);

    String newCorrectEmail = Methods.generateRandomHexString(6) + "@gmail.com";
    String newCorrectName = Methods.generateRandomHexString(6);
    String newCorrectRole = Methods.generateRandomHexString(6);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String newAvatar = Methods.uploadFile(avatarPath).jsonPath().getString("data");

    String emptyEmail = "";
    String emptyName = "";

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("Without token")
    @Description(value = "Checking the correct server response")
    @Test
    public void withoutToken() {

        Register userNewData = Register.builder().avatar(newAvatar).email(newCorrectEmail).name(newCorrectName).role(newCorrectRole).build();
        Response response = given()
                .spec(Specifications.setContentType())
                .body(userNewData)
                .when()
                .put(Routes.user)
                .then().assertThat()
                .spec(Specifications.checkStatusCode401AndContentType())
                .extract().response();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.UNAUTHORIZED), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.TOKEN_NOT_PROVIDED), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("With wrong email")
    @Description(value = "Checking the correct server response")
    @Test
    public void withWrongEmail() {

        Register userNewData = Register.builder().avatar(newAvatar).email(wrongEmail).name(newCorrectName).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        try {
            softAssert.assertTrue(codes.contains(errorCode.USER_EMAIL_NOT_VALID), "\"codes\" does not contain correct error code");
            softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("With wrong name")
    @Description(value = "Checking the correct server response")
    @Test
    public void withWrongName() {

        Register userNewData = Register.builder().avatar(newAvatar).email(newCorrectEmail).name(wrongName).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USERNAME_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("With empty email")
    @Description(value = "Checking the correct server response")
    @Test
    public void withEmptyEmail() {

        Register userNewData = Register.builder().avatar(newAvatar).email(emptyEmail).name(newCorrectName).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.EMAIL_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("With empty name")
    @Description(value = "Checking the correct server response")
    @Test
    public void withEmptyName() {

        Register userNewData = Register.builder().avatar(newAvatar).email(newCorrectEmail).name(emptyName).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USERNAME_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("Without avatar")
    @Description(value = "Checking the correct server response")
    @Test
    public void withoutAvatar() {

        Register userNewData = Register.builder().email(newCorrectEmail).name(newCorrectName).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_AVATAR_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("Without name")
    @Description(value = "Checking the correct server response")
    @Test
    public void withoutName() {

        Register userNewData = Register.builder().avatar(newAvatar).email(newCorrectEmail).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_NAME_HAS_TO_BE_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("Without email")
    @Description(value = "Checking the correct server response")
    @Test
    public void withoutEmail() {

        Register userNewData = Register.builder().avatar(newAvatar).name(newCorrectName).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_EMAIL_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("Without role")
    @Description(value = "Checking the correct server response")
    @Test
    public void withoutRole() {

        Register userNewData = Register.builder().avatar(newAvatar).email(newCorrectEmail).name(newCorrectName).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_ROLE_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("With empty name and empty email")
    @Description(value = "Checking the correct server response")
    @Test
    public void withEmptyEmailName() {

        Register userNewData = Register.builder().avatar(newAvatar).email(emptyEmail).name(emptyName).role(newCorrectRole).build();
        Response response = Methods.wrongChangeUserInfo(token, userNewData);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USERNAME_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.EMAIL_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
