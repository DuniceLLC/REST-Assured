import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class GetFileTest {
    @Test
    public void getFileTest() {
        String imagePath = "src/main/resources/postPicture.jpeg";
        String fileName = Methods.uploadFile(imagePath).jsonPath().getString("data").split("/")[6];
        Response response = given()
                .when()
                .get(Routes.getFile + fileName)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
        response.prettyPrint();
    }
}
