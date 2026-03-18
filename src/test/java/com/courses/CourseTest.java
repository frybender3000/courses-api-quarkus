package com.courses;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class CourseTest {

    @Test
    void shouldReturn415() {
        given()
                .contentType("text/plain")
                .body("invalid")
                .when()
                .post("/courses")
                .then()
                .statusCode(415);
    }
}