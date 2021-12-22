package com.learnup.tests;

import com.learnup.dto.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.RestAssured;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class PostProductTests {
    public static final String PRODUCT_ENDPOINT = "products";
    static Properties properties = new Properties();

    static Product product;
    Integer id;

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @BeforeEach
    void init_product() {
        product = Product.builder()
                .price(100)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
    }

    @Test
    void postProductPositiveTest() {
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeProductWrongStructureXSSTest() {
        given()
                .body("<script>alert(Hello, World!)</script>")
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .then()
                .contentType(ContentType.JSON);
    }

    @Test
    void postProductNegativeProductWrongStructureTest() {
        given()
                .body("string")
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek();
    }


    @Test
    void postProductNegativeIdNotNullTest() {
        product.setId(6);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativePriceNullTest() {
        product.setPrice(null);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeCategoryNotExistTest() {
        product.setCategoryTitle("plk");

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeTitleEmptyTest() {
        product.setTitle("");

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeTitleNullTest() {
        product.setTitle(null);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }


    @Test
    void postProductNegativePriceNegativeTest() {
        product.setPrice(-80);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeCategoryNullTest() {
        product.setCategoryTitle(null);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }


    @AfterEach
    void tearDown() {
        if (id != null) {
            when()
                    .delete("products/{id}", id)
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }
    }
}
