import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetPostsWithPagination {
    int perPage = 7;

    @Test
    public void getPostWithWrongPage() {
        Response response = Methods.getPostsWithPagination(1, perPage);
        int countPostsOnPage = response.jsonPath().getList("data.content").size();

        Assertions.assertEquals(countPostsOnPage,perPage);
    }

}
