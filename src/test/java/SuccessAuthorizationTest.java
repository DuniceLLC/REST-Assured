import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class SuccessAuthorizationTest {
    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";

    SoftAssertions softAssertions = new SoftAssertions();
    Methods methods = new Methods();

    @Test
    public void successAuthorizationTest() {

        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        Response response = methods.registration(user);

        String email = response.jsonPath().getString("data.email");
        String name = response.jsonPath().getString("data.name");
        String id = response.jsonPath().getString("data.id");
        String token = response.jsonPath().getString("data.token");
        String role = response.jsonPath().getString("data.role");
        String avatar = response.jsonPath().getString("data.avatar");

        Login userLogin = new Login(user.getEmail(), user.getPassword());
        Response responseLogin = methods.login(userLogin);

        int loginCustomStatusCode = responseLogin.jsonPath().getInt("statusCode");
        String loginSuccess = responseLogin.jsonPath().getString("success");
        String loginEmail = responseLogin.jsonPath().getString("data.email");
        String loginName = responseLogin.jsonPath().getString("data.name");
        String loginId = responseLogin.jsonPath().getString("data.id");
        String loginToken = responseLogin.jsonPath().getString("data.token");
        String loginRole = responseLogin.jsonPath().getString("data.role");
        String loginAvatar = responseLogin.jsonPath().getString("data.avatar");

        softAssertions.assertThat(id).isEqualTo(loginId);
        softAssertions.assertThat(avatar).isEqualTo(loginAvatar);
        softAssertions.assertThat(loginSuccess).isEqualTo("true");
        softAssertions.assertThat(loginEmail).isEqualTo(email);
        softAssertions.assertThat(loginName).isEqualTo(name);
        softAssertions.assertThat(loginToken).isEqualTo(token);
        softAssertions.assertThat(loginRole).isEqualTo(role);
//        softAssertions.assertThat(0).isEqualTo(customStatusCode);
        softAssertions.assertAll();
    }
}
