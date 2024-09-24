package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetsApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testGetPetById() {
        given()
                .pathParam("petId", 1)
        .when()
                .get("/pet/{petId}")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }

    @Test
    public void testAddPet() {
        String newPet = "{ \"id\": 1234, \"name\": \"Doggie\", \"status\": \"available\" }";

        given()
                .contentType(ContentType.JSON)
                .body(newPet)
        .when()
                .post("/pet")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1234))
                .body("name", equalTo("Doggie"))
                .body("status", equalTo("available"));
    }

    @Test
    public void testUpdatePet() {
        String updatedPet = "{ \"id\": 1234, \"name\": \"Doggie\", \"status\": \"sold\" }";

        given()
                .contentType(ContentType.JSON)
                .body(updatedPet)
        .when()
                .put("/pet")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1234))
                .body("status", equalTo("sold"));
    }

    @Test
    public void testDeletePet() {
        given()
                .pathParam("petId", 1234)
        .when()
                .delete("/pet/{petId}")
        .then()
                .statusCode(200);
    }
}
