package org.example;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class SimpleTest {

    @Test
    void testOne(){
        given()
                .get("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5")
                .then()
                .statusCode(200);
    }
}
