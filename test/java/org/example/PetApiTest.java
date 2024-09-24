package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testAddNewPet() {
        String requestBody = "{"
            + "\"id\": 12345,"
            + "\"category\": {\"id\": 1,\"name\": \"Dogs\"},"
            + "\"name\": \"Buddy\","
            + "\"photoUrls\": [\"https://example.com/dog.jpg\"],"
            + "\"tags\": [{\"id\": 0,\"name\": \"friendly\"}],"
            + "\"status\": \"available\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .body("id", equalTo(12345))
            .body("name", equalTo("Buddy"))
            .body("status", equalTo("available"));
    }

    @Test
    public void testGetPetById() {
        given()
            .pathParam("petId", 12345)
        .when()
            .get("/pet/{petId}")
        .then()
            .statusCode(200)
            .body("id", equalTo(12345))
            .body("name", equalTo("Buddy"))
            .body("status", equalTo("available"));
    }

    @Test
    public void testUpdateExistingPet() {
        String updatedPet = "{"
            + "\"id\": 12345,"
            + "\"category\": {\"id\": 1,\"name\": \"Dogs\"},"
            + "\"name\": \"Buddy\","
            + "\"photoUrls\": [\"https://example.com/dog.jpg\"],"
            + "\"tags\": [{\"id\": 0,\"name\": \"friendly\"}],"
            + "\"status\": \"sold\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .body(updatedPet)
        .when()
            .put("/pet")
        .then()
            .statusCode(200)
            .body("id", equalTo(12345))
            .body("name", equalTo("Buddy"))
            .body("status", equalTo("sold"));
    }

    @Test
    public void testFindPetsByStatus() {
        given()
            .queryParam("status", "available")
        .when()
            .get("/pet/findByStatus")
        .then()
            .statusCode(200)
            .body("", hasSize(greaterThan(0)))
            .body("[0].status", equalTo("available"));
    }
}