import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetPostsWithPagination {
    SoftAssert softAssert = new SoftAssert();
    int perPage = 7;

    @Test
    public void getPostTest() {
        Response response = Methods.getPostsWithPagination(1, perPage);
        int countPostsOnPage = response.jsonPath().getList("data.content").size();
        String success = response.jsonPath().getString("success");
        int customStatusCode = response.jsonPath().getInt("statusCode");

        softAssert.assertEquals(countPostsOnPage,perPage,"content size is not equal perPage");
        softAssert.assertEquals(success,"true","Wrong \"success\"");
        softAssert.assertEquals(customStatusCode,1,"Wrong \"statusCode\"");
        softAssert.assertEquals(countPostsOnPage,perPage,"Wrong \"success\"");
    }

}
