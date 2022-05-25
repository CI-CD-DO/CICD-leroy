package org.acme.hibernate.orm;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class HealthEndpointTest {

        @Test
        public void testListAllFruits() {
                // List all, should have all 3 fruits the database has initially:
                given()
                        .when().get("/_health")
                        .then()
                        .statusCode(204);
        }

}
