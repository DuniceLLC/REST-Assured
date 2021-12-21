import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class GetFileTest {
    @Test
    public void getFileTest() {
        String imagePath = "src/main/resources/postPicture.jpeg";
        String fileName = Methods.uploadFile(imagePath).jsonPath().getString("data").split("/")[6];
        System.out.println(fileName);
        Response response = given()
                .when()
                .get(Routes.getFile + fileName)
                .then().assertThat().statusCode(200)
                .extract().response();
        response.prettyPrint();
    }
}