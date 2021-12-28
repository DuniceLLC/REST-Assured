import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UploadFileTest {
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void uploadFileTest() {
        Response response = Methods.uploadFile("src/main/resources/postPicture.jpeg");

        String image = response.jsonPath().getString("data");
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        softAssert.assertNotNull(image, "\"image\" is null");
        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertEquals(customStatusCode,1, "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
