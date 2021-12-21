import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuccessAuthorizationTest extends SetUp {
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void successAuthorizationTest() {

        String email = response.jsonPath().getString("data.email");
        String name = response.jsonPath().getString("data.name");
        String id = response.jsonPath().getString("data.id");
        String token = response.jsonPath().getString("data.token");
        String role = response.jsonPath().getString("data.role");
        String avatar = response.jsonPath().getString("data.avatar");

        Register userLogin = Register.builder()
                .email(correctEmail)
                .password(correctPassword)
                .build();
        Response responseLogin = Methods.login(userLogin);

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
        softAssertions.assertThat(1).isEqualTo(loginCustomStatusCode);
        softAssertions.assertAll();
    }
}
