import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SuccessAuthorizationTest extends SetUp {
    SoftAssert softAssert = new SoftAssert();

    @Epic("Auth-controller")
    @Feature("Login")
    @Story("Correct request")
    @Description(value = "Login check")
    @Test
    public void successAuthorizationTest() {

        String email = response.jsonPath().getString("data.email");
        String name = response.jsonPath().getString("data.name");
        String id = response.jsonPath().getString("data.id");
        String token = response.jsonPath().getString("data.token");
        String role = response.jsonPath().getString("data.role");
        String avatar = response.jsonPath().getString("data.avatar");

        Register userLogin = Register.builder()
                .email(correctEmail)
                .password(correctPassword)
                .build();
        Response responseLogin = Methods.login(userLogin);

        int loginCustomStatusCode = responseLogin.jsonPath().getInt("statusCode");
        String loginSuccess = responseLogin.jsonPath().getString("success");
        String loginEmail = responseLogin.jsonPath().getString("data.email");
        String loginName = responseLogin.jsonPath().getString("data.name");
        String loginId = responseLogin.jsonPath().getString("data.id");
        String loginToken = responseLogin.jsonPath().getString("data.token");
        String loginRole = responseLogin.jsonPath().getString("data.role");
        String loginAvatar = responseLogin.jsonPath().getString("data.avatar");

        softAssert.assertEquals(id,loginId, "Wrong \"id\"");
        softAssert.assertEquals(avatar,loginAvatar, "Wrong \"avatar\"");
        softAssert.assertEquals(loginSuccess,"true", "Wrong \"success\"");
        softAssert.assertEquals(loginEmail,email, "Wrong \"email\"");
        softAssert.assertEquals(loginName,name, "Wrong \"name\"");
        softAssert.assertEquals(loginToken,token, "Wrong \"token\"");
        softAssert.assertEquals(loginRole,role, "Wrong \"role\"");
        softAssert.assertEquals(1,loginCustomStatusCode, "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
