import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

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

        int statusCode = response.statusCode();
        Assert.assertEquals(404, statusCode);
    }
}
