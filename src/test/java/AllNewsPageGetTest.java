import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class AllNewsPageGetTest {
    int page = 1;
    int perPage = 7;

    Routes routes = new Routes();
    SoftAssert softAssert = new SoftAssert();

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

        softAssert.assertEquals(customStatusCode, 1);
        softAssert.assertEquals(success,"true");
        softAssert.assertNotNull(numberOfElements);
        softAssert.assertFalse(content.isEmpty());
        softAssert.assertEquals(content.size(), perPage);
        softAssert.assertAll();
    }

}
