package com.learnup.tests;

import com.github.javafaker.Faker;
import com.learnup.dto.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostProductTests {
    Faker faker = new Faker();
    Product product;
    Integer id;
    @BeforeEach
    void setUp() {
        product = Product.builder()
                .price(100)
                .title(faker.food().dish())
                .categoryTitle("Food")
                .build();
    }


    //todo: refactoring
    @Test
    void postProductPositiveTest() {
        id = given()
                .body(product)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .body("title", equalTo(product.getTitle()))
                .body("price", equalTo(product.getPrice()))
                .body("categoryTitle", equalTo(product.getCategoryTitle()))
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductWithDifferentAssertsPositiveTest() {
        Product response = given()
                .body(product)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
    }

    @AfterEach
    void tearDown() {
        when()
                .delete("http://80.78.248.82:8189/market/api/v1/products/{id}", id)
        .prettyPeek()
        .then()
        .statusCode(200);
    }
}
