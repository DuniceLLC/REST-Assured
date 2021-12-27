import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import static io.restassured.RestAssured.*;

public class Methods {
    public static String generateRandomHexString(int length) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < length) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, length);
    }

    public static void showBodyPostLogin(RequestSpecification request, Routes routes) {
        request.post(Routes.login).then().
                contentType(ContentType.JSON).extract().response().prettyPrint();
    }

    public static Response deleteUser(String token) {
        return given()
                .header("Authorization", token)
                .when()
                .delete(Routes.user)
                .then().assertThat()
                .statusCode(200).extract().response();
    }

    public static Response registration(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .post(Routes.registration)
                .then().assertThat()
                .spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response wrongRegistration(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .post(Routes.registration)
                .then().assertThat()
                .spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }

    public static Response login(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .post(Routes.login)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response getPost(String author, String keywords, int page, int perPage, String[] tags) {
        return given()
                .queryParam("author", author)
                .queryParam("keywords", keywords)
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .queryParam("tags", tags)
                .get(Routes.news + "/find")
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response getPostsWithPagination(int page, int perPage) {
        return given()
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .get(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType()).extract().response();
    }

    public static Response getUserPost(int page, int perPage, String userId, String token) {
        return given()
                .header("Authorization", token)
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .get(Routes.news + "/user/" + userId)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response uploadFile(String path) {
        return given()
                .multiPart(new File(path))
                .when().post(Routes.file)
                .then().assertThat().statusCode(200)
                .extract().response();
    }

    public static Response createPost(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().post(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response wrongCreatePost(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().post(Routes.news)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }

    public static Response changePost(String token, int id, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().put(Routes.news + "/" + id)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response wrongChangePost(String token, int id, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when().put(Routes.news + "/" + id)
                .then().assertThat().spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }

    public static Response deletePost(String token, int postId) {
        return given()
                .header("Authorization", token)
                .when().delete(Routes.news + "/" + postId)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response changeUserInfo(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .put(Routes.user)
                .then().assertThat()
                .spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public static Response wrongChangeUserInfo(String token, Object obj) {
        return given()
                .header("Authorization", token)
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .put(Routes.user)
                .then().assertThat()
                .spec(Specifications.checkStatusCode400AndContentType())
                .extract().response();
    }
}
