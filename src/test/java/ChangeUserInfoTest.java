import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ChangeUserInfoTest extends SetUp {
    String avatarPathForChange = "src/main/resources/avatar-2.jpeg";
    String avatar = Methods.uploadFile(avatarPathForChange).jsonPath().getString("data");
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void changeUserInfoSuccess() {
        String idReg = response.jsonPath().getString("data.id");

        String newEmail = Methods.generateRandomHexString(5) + "@gmail.com";
        String newName = Methods.generateRandomHexString(5);

        Register userNewData = Register.builder()
                .avatar(avatar)
                .email(newEmail)
                .name(newName)
                .role(correctRole)
                .build();

        Response responseChange = Methods.changeUserInfo(token, userNewData);

        String success = responseChange.jsonPath().getString("success");
        String avatar = responseChange.jsonPath().getString("data.avatar");
        String email = responseChange.jsonPath().getString("data.email");
        String id = responseChange.jsonPath().getString("data.id");
        String name = responseChange.jsonPath().getString("data.name");
        String role = responseChange.jsonPath().getString("data.role");
        int customStatusCode = responseChange.jsonPath().getInt("statusCode");

        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertThat(role).isEqualTo(correctRole);
        softAssertions.assertThat(name).isEqualTo(newName);
        softAssertions.assertThat(id).isEqualTo(idReg);
        softAssertions.assertThat(email).isEqualTo(newEmail);
        softAssertions.assertThat(avatar).isEqualTo(avatar);
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertAll();
    }
}
