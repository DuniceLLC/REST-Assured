import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

import static io.restassured.RestAssured.given;

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

    public static void showBodyPostLogin(RequestSpecification request, Routes routes) {
        request.post(routes.postLogin).then().
                contentType(ContentType.JSON).extract().response().prettyPrint();
    }

    public Response deleteUser(String token) {
       return given()
                .header("Authorization", token)
                .when()
                .delete(routes.deleteDeleteUser)
                .then().assertThat()
                .statusCode(200).extract().response();
    }

    public Response registration(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .when()
                .post(routes.postRegistration)
                .then().assertThat()
                .spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

    public Response login(Object obj) {
        return given()
                .spec(Specifications.setContentType())
                .body(obj)
                .post(routes.postLogin)
                .then().assertThat().spec(Specifications.checkStatusCode200AndContentType())
                .extract().response();
    }

}
