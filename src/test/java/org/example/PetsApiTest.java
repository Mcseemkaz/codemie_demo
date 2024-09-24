package org.example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PetsApiTest {

    private static final String BASE_URL = "https://api.example.com/v1";

    @BeforeAll
    public static void setup() {
        baseURI = BASE_URL;
    }

    @Test
    public void testGetAllPets() {
        given()
            .when()
                .get("/pets")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetPetById() {
        int petId = 1;
        given()
            .pathParam("id", petId)
        .when()
            .get("/pets/{id}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(petId))
            .body("name", notNullValue())
            .body("status", notNullValue());
    }

    @Test
    public void testCreatePet() {
        String newPet = "{"
            + "\"name\": \"Fluffy\","
            + "\"status\": \"available\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .body(newPet)
        .when()
            .post("/pets")
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", notNullValue())
            .body("name", equalTo("Fluffy"))
            .body("status", equalTo("available"));
    }

    @Test
    public void testUpdatePet() {
        int petId = 1;
        String updatedPet = "{"
            + "\"name\": \"Fluffy\","
            + "\"status\": \"sold\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .body(updatedPet)
            .pathParam("id", petId)
        .when()
            .put("/pets/{id}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(petId))
            .body("name", equalTo("Fluffy"))
            .body("status", equalTo("sold"));
    }

    @Test
    public void testDeletePet() {
        int petId = 1;

        given()
            .pathParam("id", petId)
        .when()
            .delete("/pets/{id}")
        .then()
            .statusCode(204);

        // Verify the pet is deleted
        given()
            .pathParam("id", petId)
        .when()
            .get("/pets/{id}")
        .then()
            .statusCode(404);
    }
}