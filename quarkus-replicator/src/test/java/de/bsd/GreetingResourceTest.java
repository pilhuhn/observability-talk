package de.bsd;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/tea?kind=sencha")
          .then()
             .statusCode(200);
    }

    @Test
    void testForSpace() {
        given()
                .when().get("/tea?kind=earl%20grey")
                .then()
                .statusCode(402);
    }
}
