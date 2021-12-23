import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class GetUserInfoTest extends SetUp {
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void getUserInfoByIdTest() {
        String id = response.jsonPath().getString("data.id");
        String avatar = response.jsonPath().getString("data.avatar");
        String email = response.jsonPath().getString("data.email");
        String name = response.jsonPath().getString("data.name");
        String role = response.jsonPath().getString("data.role");

        Response responseInfo =
                get(Routes.user + "/" + id)
                        .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                        .extract().response();

        String avatarInfo = responseInfo.jsonPath().getString("data.avatar");
        String emailInfo = responseInfo.jsonPath().getString("data.email");
        String idInfo = responseInfo.jsonPath().getString("data.id");
        String nameInfo = responseInfo.jsonPath().getString("data.name");
        String roleInfo = responseInfo.jsonPath().getString("data.role");
        int customStatusCodeInfo = responseInfo.jsonPath().getInt("statusCode");
        String successInfo = responseInfo.jsonPath().getString("success");

        softAssertions.assertThat(avatarInfo).isEqualTo(avatar);
        softAssertions.assertThat(emailInfo).isEqualTo(email);
        softAssertions.assertThat(nameInfo).isEqualTo(name);
        softAssertions.assertThat(idInfo).isEqualTo(id);
        softAssertions.assertThat(roleInfo).isEqualTo(role);
        softAssertions.assertThat(customStatusCodeInfo).isEqualTo(1);
        softAssertions.assertThat(successInfo).isEqualTo("true");
        softAssertions.assertAll();
    }

    @Test
    public void getUsers() {
        Response response =given()
                .header("Authorization", token)
                .get(Routes.user)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();

        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Object> users = response.jsonPath().getList("data");

        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(users).isNotNull();
        softAssertions.assertAll();
    }
}
