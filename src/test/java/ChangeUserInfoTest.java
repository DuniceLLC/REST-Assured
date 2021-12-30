import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ChangeUserInfoTest extends SetUp {
    String avatarPathForChange = "src/main/resources/avatar-2.jpeg";
    String avatar = Methods.uploadFile(avatarPathForChange).jsonPath().getString("data");

    @Epic("User-controller")
    @Feature("Change user info")
    @Story("Correct request")
    @Description(value = "Change check user info")
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

        softAssert.assertEquals(customStatusCode,1, "Wrong \"statusCode\"");
        softAssert.assertEquals(role,correctRole, "Wrong \"role\"");
        softAssert.assertEquals(name,newName, "Wrong \"name\"");
        softAssert.assertEquals(id,idReg,"Wrong \"id\"");
        softAssert.assertEquals(email,newEmail,"Wrong \"email\"");
        softAssert.assertEquals(avatar,this.avatar,"Wrong \"avatar\"");
        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertAll();
    }
}
