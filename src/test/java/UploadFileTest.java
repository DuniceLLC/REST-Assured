import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UploadFileTest {
    SoftAssert softAssert = new SoftAssert();
    Methods methods = new Methods();

    @Epic("File-controller")
    @Feature("Upload file")
    @Story("Correct request")
    @Description(value = "File upload check")
    @Test
    public void uploadFileTest() {
        Response response = methods.uploadFile("src/main/resources/postPicture.jpeg");

        String image = response.jsonPath().getString("data");
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        softAssert.assertNotNull(image, "\"image\" is null");
        softAssert.assertEquals(success,"true", "Wrong \"success\"");
        softAssert.assertEquals(customStatusCode,1, "Wrong \"statusCode\"");
        softAssert.assertAll();
    }
}
