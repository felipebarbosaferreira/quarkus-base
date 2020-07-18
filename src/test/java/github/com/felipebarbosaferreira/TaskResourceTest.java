package github.com.felipebarbosaferreira;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TaskResourceTest {

    @Test
    public void testGetAllEndpoint() {
        given()
          .when().get("/task")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }

}