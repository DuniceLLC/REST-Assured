import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import static io.restassured.RestAssured.*;

public class DeleteUserTest {

    String correctEmail = Methods.generateRandomHexString(5) + "@gmail.com";
    String correctPassword = Methods.generateRandomHexString(5);
    String avatarPath = "src/main/resources/avatar.jpeg";
    String correctName = Methods.generateRandomHexString(5);
    String correctRole = "user";
    String emptyToken = "";

    SoftAssert softAssert = new SoftAssert();
    ErrorCode errorCode =new ErrorCode();

    @Test
    public void deleteUser() {
        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        Response response = Methods.registration(user);

        String token = response.jsonPath().getString("data.token");
        Response responseDelete = Methods.deleteUser(token);
        int customStatusCode = responseDelete.jsonPath().getInt("statusCode");
        String success = responseDelete.jsonPath().getString("success");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertEquals(customStatusCode,1,"Wrong \"statusCode\"");
        softAssert.assertAll();
    }

    @Test
    public void deleteWithEmptyToken() {
        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        String token = Methods.registration(user).jsonPath().getString("data.token");

        Response responseDelete = given()
                .header("Authorization", emptyToken)
                .delete(Routes.user)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType()).extract().response();

        int customStatusCode = responseDelete.jsonPath().getInt("statusCode");
        String success = responseDelete.jsonPath().getString("success");
        List<Integer> codes = responseDelete.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.UNAUTHORIZED), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
        Methods.deleteUser(token);
    }

    @Test
    public void deleteWithoutToken() {
        Register user = new Register(avatarPath, correctEmail, correctName, correctPassword, correctRole);
        String token = Methods.registration(user).jsonPath().getString("data.token");

        Response responseDelete =
                delete(Routes.user)
                .then().assertThat().spec(Specifications.checkStatusCode401AndContentType()).extract().response();

        int customStatusCode = responseDelete.jsonPath().getInt("statusCode");
        String success = responseDelete.jsonPath().getString("success");
        List<Integer> codes = responseDelete.jsonPath().getList("codes");

        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertTrue(codes.contains(errorCode.UNAUTHORIZED), "\"codes\" does not contain correct error code");
        softAssert.assertEquals(customStatusCode,codes.get(0).intValue(),"Wrong \"statusCode\"");
        softAssert.assertAll();
        Methods.deleteUser(token);
    }
}
