import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class UploadFileTest {
    SoftAssertions softAssertions = new SoftAssertions();
    @Test
    public void uploadFileTest() {
        Response response = Methods.uploadFile("src/main/resources/postPicture.jpeg");

        String image = response.jsonPath().getString("data");
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        softAssertions.assertThat(image).isNotNull();
        softAssertions.assertThat(success).isEqualTo("true");
        softAssertions.assertThat(customStatusCode).isEqualTo(1);
        softAssertions.assertAll();
    }
}
