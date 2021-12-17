import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class LoginPageGetTest {

    Routes routes = new Routes();

    @Test
    public void loginPageGetTest() {
        RestAssured.
                when()
                .get(routes.loginPage)
                .then().assertThat().statusCode(200)
                .extract()
                .response()
                .prettyPrint();
    }
}
