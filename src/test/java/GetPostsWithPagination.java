import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetPostsWithPagination {
    int perPage = 7;

    @Test
    public void getPostWithWrongPage() {
        Response response = Methods.getPostsWithPagination(1, perPage);
        int countPostsOnPage = response.jsonPath().getList("data.content").size();

        Assert.assertEquals(countPostsOnPage,perPage);
    }

}
