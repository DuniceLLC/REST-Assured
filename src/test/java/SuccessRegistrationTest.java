import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class SuccessRegistrationTest {
    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";

    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void registrationTest() {
        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        Response response = Methods.registration(user);

        int customStatusCode = response.jsonPath().getInt("statusCode");
        String success = response.jsonPath().getString("success");
        String email = response.jsonPath().getString("data.email");
        String name = response.jsonPath().getString("data.name");
        String id = response.jsonPath().getString("data.id");
        String token = response.jsonPath().getString("data.token");
        String role = response.jsonPath().getString("data.role");
        String avatar = response.jsonPath().getString("data.avatar");

        softAssertions.assertThat(id).isNotNull();
        softAssertions.assertThat(token).isNotNull();
        softAssertions.assertThat(avatar).isNotNull();
        softAssertions.assertThat(correctRole).isEqualTo(role);
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(correctEmail).isEqualTo(email);
        softAssertions.assertThat(correctName).isEqualTo(name);
        softAssertions.assertThat(1).isEqualTo(customStatusCode);
        softAssertions.assertAll();
    }
}
