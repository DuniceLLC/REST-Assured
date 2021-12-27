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

        softAssert.assertNotNull(id);
        softAssert.assertNotNull(token);
        softAssert.assertNotNull(avatar);
        softAssert.assertEquals(correctRole,role);
        softAssert.assertEquals("true",success);
        softAssert.assertEquals(correctEmail,email);
        softAssert.assertEquals(correctName,name);
        softAssert.assertEquals(customStatusCode,1);
        softAssert.assertAll();
    }
}
