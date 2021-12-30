import io.restassured.response.*;
import lombok.Getter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

@Getter
public class SetUp {
    Methods methods = new Methods();
    Routes routes = new Routes();
    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String avatar = methods.uploadFile(avatarPath).jsonPath().getString("data");
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";
    String token;
    Register user = new Register(avatar, correctEmail, correctName, correctPassword, correctRole);
    Response response;
    SoftAssert softAssert;

    @BeforeMethod
    public void reg() {
        softAssert = new SoftAssert();
        response = methods.registration(user);
        token = response.jsonPath().getString("data.token");
    }

    @AfterMethod
    public void delete() {
        methods.deleteUser(token);
    }
}
