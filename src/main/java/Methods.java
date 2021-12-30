import io.restassured.response.Response;
import java.io.File;
import java.util.Random;
import static io.restassured.RestAssured.*;

public class Methods {
    Routes routes = new Routes();
    public static String generateRandomHexString(int length) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < length) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, length);
    }

    public Response deleteUser(String token) {
        return given()
                .header("Authorization", token)
                .when()
                .delete(routes.getUser())
                .then().assertThat()
                .statusCode(200).extract().response();
    }

    public Response registration(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .post(routes.getRegistration())
                .then().assertThat()
                .spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response wrongRegistration(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .post(routes.getRegistration())
                .then().assertThat()
                .spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }

    public Response login(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .post(routes.getLogin())
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response getPost(String author, String keywords, int page, int perPage, String[] tags) {
        return given()
                .queryParam("author", author)
                .queryParam("keywords", keywords)
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .queryParam("tags", tags)
                .get(routes.getNews() + "/find")
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response getPostsWithPagination(int page, int perPage) {
        return given()
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .get(routes.getNews())
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType()).extract().response();
    }

    public Response getUserPost(int page, int perPage, String userId, String token) {
        return given()
                .header("Authorization", token)
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .get(routes.getNews() + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response uploadFile(String path) {
        return given()
                .multiPart(new File(path))
                .when().post(routes.getFile())
                .then().assertThat().statusCode(200)
                .extract().response();
    }

    public Response createPost(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().post(routes.getNews())
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response wrongCreatePost(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().post(routes.getNews())
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }

    public Response changePost(String token, int id, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().put(routes.getNews() + "/" + id)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response wrongChangePost(String token, int id, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().put(routes.getNews() + "/" + id)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }

    public Response deletePost(String token, int postId) {
        return given()
                .header("Authorization", token)
                .when().delete(routes.getNews() + "/" + postId)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response changeUserInfo(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .put(routes.getUser())
                .then().assertThat()
                .spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response wrongChangeUserInfo(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .put(routes.getUser())
                .then().assertThat()
                .spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }
}
