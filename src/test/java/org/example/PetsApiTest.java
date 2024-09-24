package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetsApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://example-pets-api.com/api";
    }

    @Test
    public void testCreatePet() {
        String petJson = "{ \"name\": \"Buddy\", \"type\": \"Dog\", \"age\": 3 }";
        given()
            .contentType(ContentType.JSON)
            .body(petJson)
        .when()
            .post("/pets")
        .then()
            .statusCode(201)
            .body("name", equalTo("Buddy"));
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
            .body("id", equalTo(petId));
    }

    @Test
    public void testUpdatePet() {
        int petId = 1;
        String updatedPetJson = "{ \"name\": \"Buddy\", \"type\": \"Dog\", \"age\": 4 }";
        given()
            .contentType(ContentType.JSON)
            .body(updatedPetJson)
            .pathParam("id", petId)
        .when()
            .put("/pets/{id}")
        .then()
            .statusCode(200)
            .body("age", equalTo(4));
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
    }

    @Test
    public void testGetAllPets() {
        given()
        .when()
            .get("/pets")
        .then()
            .statusCode(200);
    }
}
