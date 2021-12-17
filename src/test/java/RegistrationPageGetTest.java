import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class RegistrationPageGetTest {

    Routes routes = new Routes();

    @Test
    public void registrationPageGetTest() {
        RestAssured
                .when().get(routes.registrationPage)
                .then().assertThat().statusCode(200)
                .extract()
                .response()
                .prettyPrint();
    }
}
