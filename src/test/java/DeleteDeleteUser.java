import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class DeleteDeleteUser {

    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";

    SoftAssertions softAssertions = new SoftAssertions();

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
}
