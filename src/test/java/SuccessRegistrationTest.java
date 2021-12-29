import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SuccessRegistrationTest {
    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";

    SoftAssert softAssert = new SoftAssert();

    @Epic("Auth-controller")
    @Feature("Registration")
    @Story("Correct request")
    @Description(value = "Registration check")
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

        softAssert.assertNotNull(id, "\"id\" is null");
        softAssert.assertNotNull(token, "\"token\" is null");
        softAssert.assertNotNull(avatar, "\"avatar\" is null");
        softAssert.assertEquals(correctRole,role, "Wrong \"role\"");
        softAssert.assertEquals("true",success, "Wrong \"success\"");
        softAssert.assertEquals(correctEmail,email, "Wrong \"email\"");
        softAssert.assertEquals(correctName,name, "Wrong \"name\"");
        softAssert.assertEquals(customStatusCode,1, "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
