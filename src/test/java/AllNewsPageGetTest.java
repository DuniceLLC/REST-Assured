import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class AllNewsPageGetTest {
    int page = 1;
    int perPage = 7;

    Routes routes = new Routes();
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void allNewsPageGetTest() {
        Response response = given()
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .when()
                .get(routes.getNews)
                .then().assertThat().statusCode(200)
                .and().body("success", is(true))
                .and().body("statusCode", is(1))
                .extract()
                .response();

        List<Object> content = response.jsonPath().getList("data.content");
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        int numberOfElements = response.jsonPath().getInt("data.numberOfElements");

        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(numberOfElements).isNotNull();
        softAssertions.assertThat(content).isNotEmpty();
        softAssertions.assertThat(content).hasSize(perPage);
        softAssertions.assertAll();
    }

}
