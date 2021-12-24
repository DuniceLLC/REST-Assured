import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import java.util.List;

import static io.restassured.RestAssured.given;

public class WrongChangeUserInfoTest extends SetUp {
    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode = new ErrorCode();

    String wrongEmail = Methods.generateRandomHexString(5);
    String wrongName = Methods.generateRandomHexString(2);

    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String avatar = Methods.uploadFile(avatarPath).jsonPath().getString("data");

    String emptyEmail = "";
    String emptyName = "";

    @Test
    public void withoutToken() {
        Register userNewData = Register.builder().avatar(avatar).email(correctEmail).name(correctName).role(correctRole).build();
        Response response = given()
                .spec(Specifications.setContentType())
                .body(userNewData)
                .when()
                .put(Routes.user)
                .then().assertThat()
                .spec(Specifications.checkStatusCode401AndContentType())
                .extract().response();
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.UNAUTHORIZED, errorCode.TOKEN_NOT_PROVIDED);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withWrongEmail() {
        Register userNewData = Register.builder().avatar(avatar).email(wrongEmail).name(correctName).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_VALID);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withWrongName() {
        Register userNewData = Register.builder().avatar(avatar).email(correctEmail).name(wrongName).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USERNAME_SIZE_NOT_VALID);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withEmptyEmail() {
        Register userNewData = Register.builder().avatar(avatar).email(emptyEmail).name(correctName).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.EMAIL_SIZE_NOT_VALID);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withEmptyName() {
        Register userNewData = Register.builder().avatar(avatar).email(correctEmail).name(emptyName).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USERNAME_SIZE_NOT_VALID);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withoutAvatar() {
        Register userNewData = Register.builder().email(correctEmail).name(correctName).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USER_AVATAR_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withoutName() {
        Register userNewData = Register.builder().avatar(avatar).email(correctEmail).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USER_NAME_HAS_TO_BE_PRESENT);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withoutEmail() {
        Register userNewData = Register.builder().avatar(avatar).name(correctName).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USER_EMAIL_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withoutRole() {
        Register userNewData = Register.builder().avatar(avatar).email(correctEmail).name(correctName).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USER_ROLE_NOT_NULL);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }

    @Test
    public void withEmptyEmailName() {
        Register userNewData = Register.builder().avatar(avatar).email(emptyEmail).name(emptyName).role(correctRole).build();
        Response response = Methods.wrongChangeUserInfo(token,userNewData);
        response.prettyPrint();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.USERNAME_SIZE_NOT_VALID, errorCode.EMAIL_SIZE_NOT_VALID);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
    }
}
