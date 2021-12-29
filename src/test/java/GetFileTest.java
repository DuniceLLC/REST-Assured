import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class GetFileTest {

    @Epic("File-controller")
    @Feature("Get file")
    @Story("Correct request")
    @Description(value = "File get check")
    @Test
    public void getFileTest() {
        String imagePath = "src/main/resources/postPicture.jpeg";
        String fileName = Methods.uploadFile(imagePath).jsonPath().getString("data").split("/")[6];
        Response response = given()
                .when()
                .get(Routes.getFile + fileName)
                .then().assertThat()
                .extract().response();

        int statusCode = response.statusCode();

        Assert.assertEquals(statusCode, 200, "Wrong status code");
    }
}
