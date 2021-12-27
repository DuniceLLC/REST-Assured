import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import static io.restassured.RestAssured.*;

public class GetUserInfoTest extends SetUp {
    SoftAssert softAssert = new SoftAssert();

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

        softAssert.assertEquals(avatarInfo,avatar);
        softAssert.assertEquals(emailInfo,email);
        softAssert.assertEquals(nameInfo,name);
        softAssert.assertEquals(idInfo,id);
        softAssert.assertEquals(roleInfo,role);
        softAssert.assertEquals(customStatusCodeInfo,1);
        softAssert.assertEquals(successInfo,"true");
        softAssert.assertAll();
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

        softAssert.assertEquals(customStatusCode,1);
        softAssert.assertEquals(success,"true");
        softAssert.assertNotNull(users);
        softAssert.assertAll();
    }
}
