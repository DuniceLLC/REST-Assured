import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class WrongRegistrationTest {
    SoftAssert softAssert;
    ErrorCode errorCode = new ErrorCode();
    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = Methods.generateRandomHexString(5);
    String correctPassword = Methods.generateRandomHexString(6);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String avatar = Methods.uploadFile(avatarPath).jsonPath().getString("data");

    String emptyRole = "";
    String emptyAvatar = "";
    String emptyEmail = "";
    String emptyName = "";
    String emptyPassword = "";
    String wrongName = Methods.generateRandomHexString(101);
    String wrongRole = Methods.generateRandomHexString(2);

    @BeforeMethod
    public void createSoftAssert() {
        softAssert = new SoftAssert();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With empty all fields")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithEmptyAllFields() {
        Register user = new Register(emptyAvatar, emptyEmail, emptyName, emptyPassword, emptyRole);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.ROLE_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.USERNAME_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.EMAIL_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With null all fields")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithNullAllFields() {
        Register user = new Register(null, null, null, null, null);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.MUST_NOT_BE_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.USER_AVATAR_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.PASSWORD_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertTrue(codes.contains(errorCode.USER_NAME_HAS_TO_BE_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With empty email")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithEmptyEmail() {
        Register user = new Register(avatar, emptyEmail, correctName, correctPassword, correctRole);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.EMAIL_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("Without avatar")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithoutAvatar() {
        Register user = Register.builder().email(correctEmail).name(correctName).password(correctPassword).role(correctRole).build();
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_AVATAR_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("Without email")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithoutEmail() {
        Register user = Register.builder().avatar(avatar).name(correctName).password(correctPassword).role(correctRole).build();
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_EMAIL_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With empty name")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithEmptyName() {
        Register user = new Register(avatar, correctEmail, emptyName, correctPassword, correctRole);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USERNAME_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("Without name")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithoutName() {
        Register user = Register.builder().avatar(avatar).email(correctEmail).password(correctPassword).role(correctRole).build();
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_NAME_HAS_TO_BE_PRESENT), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With empty password")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithEmptyPassword() {
        Register user = new Register(avatar, correctEmail, correctName, emptyPassword, correctRole);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.PASSWORD_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("Without password")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithoutPassword() {
        Register user = Register.builder().avatar(avatar).email(correctEmail).name(correctName).role(correctRole).build();
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.PASSWORD_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With empty role")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithEmptyRole() {
        Register user = new Register(avatar, correctEmail, correctName, correctPassword, emptyRole);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.ROLE_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("Without role")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithoutRole() {
        Register user = Register.builder().avatar(avatar).email(correctEmail).name(correctName).password(correctPassword).build();
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_ROLE_NOT_NULL), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With existing email")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithExistingEmail() {
        Register user = new Register(avatar, correctEmail, correctName, correctPassword, correctRole);
        Methods.registration(user);
        Response response2 = Methods.wrongRegistration(user);
        String success = response2.jsonPath().getString("success");
        int customStatusCode = response2.jsonPath().getInt("statusCode");
        List<Integer> codes = response2.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USER_ALREADY_EXISTS), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With wrong name")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithWrongName() {
        Register user = new Register(avatar, correctEmail, wrongName, correctPassword, correctRole);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.USERNAME_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("With wrong role")
    @Description(value = "Checking the correct server response")
    @Test
    public void regWithWrongRole() {
        Register user = new Register(avatar, correctEmail, correctName, correctPassword, wrongRole);
        Response response = Methods.wrongRegistration(user);
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");

        softAssert.assertEquals(success, "true", "Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.ROLE_SIZE_NOT_VALID), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode, codes.get(0).intValue(), "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
