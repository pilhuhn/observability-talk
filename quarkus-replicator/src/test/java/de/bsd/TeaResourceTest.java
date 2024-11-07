package de.bsd;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TeaResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/tea?kind=sencha")
          ;
    }

    @Test
    void testForSpace() {
        given()
                .when().get("/tea?kind=earl%20grey")
                ;
    }
}
