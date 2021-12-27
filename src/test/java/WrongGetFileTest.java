import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class WrongGetFileTest {
    String emptyFileName = "";
    @Test
    public void getFileWithoutFileNameTest() {
        String imagePath = "src/main/resources/postPicture.jpeg";
        Methods.uploadFile(imagePath);
        Response response = given()
                .when()
                .get(Routes.getFile + emptyFileName)
                .then()
                .extract().response();

        int ststusCode = response.statusCode();
        Assertions.assertEquals(404, ststusCode);
    }
}
