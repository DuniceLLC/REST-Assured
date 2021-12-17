import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {
    public static ResponseSpecification checkStatusCode200AndContentType() {
        return new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    public static RequestSpecification setContentType() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }
}

