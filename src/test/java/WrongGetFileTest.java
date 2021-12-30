import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class WrongGetFileTest {
    Methods methods = new Methods();
    Routes routes = new Routes();
    String emptyFileName = "";

    @Epic("File-controller")
    @Feature("Get file")
    @Story("Without file name")
    @Description(value = "Checking the correct server response")
    @Test
    public void getFileWithoutFileNameTest() {
        String imagePath = "src/main/resources/postPicture.jpeg";
        methods.uploadFile(imagePath);
        Response response = given()
                .when()
                .get(routes.getGetFile() + emptyFileName)
                .then()
                .extract().response();

        int statusCode = response.statusCode();
        Assert.assertEquals(404, statusCode);
    }
}
