import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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

    @Epic("News-controller")
    @Feature("Get news")
    @Story("Correct request")
    @Description(value = "Checking get news")
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

        softAssert.assertEquals(customStatusCode, 1, "Wrong \"statusCode\"");
        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertNotNull(numberOfElements, "\"numberOfElements\" is null");
        softAssert.assertFalse(content.isEmpty(), "\"content is empty\"");
        softAssert.assertEquals(content.size(), perPage, "content size is not equal perPage");
        softAssert.assertAll();
    }
}
