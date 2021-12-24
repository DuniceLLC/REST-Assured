import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class WrongGetUserInfoTest {
    SoftAssertions softAssertions = new SoftAssertions();
    ErrorCode errorCode = new ErrorCode();
    String wrongUserId = Methods.generateRandomHexString(8) + "-" +
            Methods.generateRandomHexString(4) + "-" +
            Methods.generateRandomHexString(4) + "-" +
            Methods.generateRandomHexString(4) + "-" +
            Methods.generateRandomHexString(12);

    @Test
    public void withWrongUserId() {
        Response response = get(Routes.user + "/" + wrongUserId)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");
        List<Integer> codes = response.jsonPath().getList("codes");
        softAssertions.assertThat("true").isEqualTo(success);
        softAssertions.assertThat(customStatusCode).isEqualTo(codes.get(0));
        softAssertions.assertThat(codes).contains(errorCode.USER_NOT_FOUND);
        softAssertions.assertAll();
    }
}
