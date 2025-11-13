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
                .then()
                .statusCode(200)
          ;
    }

    @Test
    void testForAmpSpace() {
        given()
                .when().get("/tea?kind=earl%20grey")
                .then()
                .statusCode(402)
                ;
    }

    @Test
    void testForSpace() {
        given()
                .when().get("/tea?kind=earl grey")
                .then()
                .statusCode(402)
                ;
    }

    @Test
    void testForNoInput() {
         given()
                 .when().get("/tea?kind=")
                 .then()
                 .statusCode(500)
                 ;
     }
}
