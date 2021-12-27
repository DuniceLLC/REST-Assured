import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ChangeUserInfoTest extends SetUp {
    String avatarPathForChange = "src/main/resources/avatar-2.jpeg";
    String avatar = Methods.uploadFile(avatarPathForChange).jsonPath().getString("data");
    SoftAssert softAssert = new SoftAssert();

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

        softAssert.assertEquals(customStatusCode,1);
        softAssert.assertEquals(role,correctRole);
        softAssert.assertEquals(name,newName);
        softAssert.assertEquals(id,idReg);
        softAssert.assertEquals(email,newEmail);
        softAssert.assertEquals(avatar,this.avatar);
        softAssert.assertEquals(success,"true");
        softAssert.assertAll();
    }
}
