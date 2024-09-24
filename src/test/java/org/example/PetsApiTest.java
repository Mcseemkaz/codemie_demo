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
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void addNewPet() {
        String requestBody = "{" +
                "\"id\": 0," +
                "\"category\": { \"id\": 0, \"name\": \"string\" }," +
                "\"name\": \"doggie\"," +
                "\"photoUrls\": [\"string\"]," +
                "\"tags\": [{ \"id\": 0, \"name\": \"string\" }]," +
                "\"status\": \"available\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);
    }

    @Test
    public void updateExistingPet() {
        String requestBody = "{" +
                "\"id\": 1," +
                "\"category\": { \"id\": 0, \"name\": \"string\" }," +
                "\"name\": \"doggie\"," +
                "\"photoUrls\": [\"string\"]," +
                "\"tags\": [{ \"id\": 0, \"name\": \"string\" }]," +
                "\"status\": \"available\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200);
    }

    @Test
    public void findPetsByStatus() {
        given()
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue());
    }

    @Test
    public void findPetsByTags() {
        given()
                .queryParam("tags", "tag1,tag2")
                .when()
                .get("/pet/findByTags")
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue());
    }

    @Test
    public void findPetById() {
        int petId = 1;
        given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(petId));
    }

    @Test
    public void updatePetWithForm() {
        int petId = 1;
        given()
                .contentType(ContentType.URLENC)
                .pathParam("petId", petId)
                .formParam("name", "doggie")
                .formParam("status", "available")
                .when()
                .post("/pet/{petId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void deletePet() {
        int petId = 1;
        given()
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void uploadPetImage() {
        int petId = 1;
        given()
                .multiPart("file", "image.jpg")
                .pathParam("petId", petId)
                .when()
                .post("/pet/{petId}/uploadImage")
                .then()
                .statusCode(200);
    }
}
