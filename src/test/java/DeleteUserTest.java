import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class DeleteUserTest {

    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";
    String emptyToken = "";

    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode =new ErrorCode();

    @Test
    public void deleteUser() {
        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        Response response = Methods.registration(user);

        String token = response.jsonPath().getString("data.token");
        Response responseDelete = Methods.deleteUser(token);
        int customStatusCode = responseDelete.jsonPath().getInt("statusCode");
        String success = responseDelete.jsonPath().getString("success");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertAll();
    }

    @Test
    public void deleteWithEmptyToken() {
        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        String token = Methods.registration(user).jsonPath().getString("data.token");

        Response responseDelete = given()
                .header("Authorization", emptyToken)
                .delete(Routes.user)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType()).extract().response();

        int customStatusCode = responseDelete.jsonPath().getInt("statusCode");
        String success = responseDelete.jsonPath().getString("success");
        List<Integer> codes = responseDelete.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.UNAUTHORIZED);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
        Methods.deleteUser(token);
    }

    @Test
    public void deleteWithoutToken() {
        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        String token = Methods.registration(user).jsonPath().getString("data.token");

        Response responseDelete =
                delete(Routes.user)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType()).extract().response();

        int customStatusCode = responseDelete.jsonPath().getInt("statusCode");
        String success = responseDelete.jsonPath().getString("success");
        List<Integer> codes = responseDelete.jsonPath().getList("codes");

        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(codes).contains(errorCode.UNAUTHORIZED);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertAll();
        Methods.deleteUser(token);
    }
}
