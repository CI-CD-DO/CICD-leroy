package org.acme.hibernate.orm;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CityEndpointTest {

        @Test
        public void testAddNewCity() {
                // List all, should have Montpellier the database has initially:
                given()
                                .when().get("/city")
                                .then()
                                .statusCode(200)
                                .body(
                                                containsString("Montpellier"),
                                                containsString("Paris"),
                                                containsString("Avignon"));

                // Create Bordeaux:
                given()
                                .when()
                                .body("{\"name\" : \"Bordeaux\", \"department_code\" : \"33800\", \"insee_code\" : \"33063\", \"zip_code\" : \"33000\", \"lat\" : 44.837789, \"lon\" : -0.57918}")
                                .contentType("application/json")
                                .post("/city")
                                .then()
                                .statusCode(201);

                // List all, Bordeaux should be present now:
                given()
                                .when().get("/city")
                                .then()
                                .statusCode(200)
                                .body(
                                                containsString("Montpellier"),
                                                containsString("Paris"),
                                                containsString("Avignon"),
                                                containsString("Bordeaux"));

                // List all, cherry should be missing now:
                // given()
                // .when().get("/city")
                // .then()
                // .statusCode(200)
                // .body(
                // containsString("Montpellier"),
                // containsString("Paris"),
                // containsString("Avignon"),
                // not(containsString("Bordeaux"))
                // );
        }

        @Test
        public void testListAllCity() {
                // List all, should have Montpellier the database has initially:
                given()
                                .when().get("/city")
                                .then()
                                .statusCode(200)
                                .body(
                                                containsString("Montpellier"),
                                                containsString("Paris"),
                                                containsString("Avignon"));
        }

}
