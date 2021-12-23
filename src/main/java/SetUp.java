import io.restassured.response.*;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@Getter
public class SetUp {
    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String avatar = Methods.uploadFile(avatarPath).jsonPath().getString("data");
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";
    String token;
    Register user = new Register(avatar, correctEmail, correctName, correctPassword, correctRole);
    Response response;

    @BeforeEach
    public void reg() {
        response = Methods.registration(user);
        token = response.jsonPath().getString("data.token");
    }

    @AfterEach
    public void delete() {
        Methods.deleteUser(token);
    }
}
