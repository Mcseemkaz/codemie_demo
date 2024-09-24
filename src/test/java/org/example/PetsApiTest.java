package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PetsApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.example.com"; // Replace with actual base URI
    }

    @Test
    public void testGetPetById() {
        given()
            .pathParam("petId", 1)
        .when()
            .get("/pets/{petId}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("name", notNullValue());
    }

    @Test
    public void testCreatePet() {
        String newPet = "{ \"name\": \"Fluffy\", \"type\": \"Dog\", \"age\": 3 }";

        given()
            .contentType(ContentType.JSON)
            .body(newPet)
        .when()
            .post("/pets")
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", notNullValue())
            .body("name", equalTo("Fluffy"));
    }

    @Test
    public void testUpdatePet() {
        String updatedPet = "{ \"name\": \"Fluffy\", \"type\": \"Dog\", \"age\": 4 }";

        given()
            .pathParam("petId", 1)
            .contentType(ContentType.JSON)
            .body(updatedPet)
        .when()
            .put("/pets/{petId}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("age", equalTo(4));
    }

    @Test
    public void testDeletePet() {
        given()
            .pathParam("petId", 1)
        .when()
            .delete("/pets/{petId}")
        .then()
            .statusCode(204);
    }
}