package com.learnup.tests;

import com.learnup.dto.Category;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.learnup.enums.CategoryType.FOOD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CategoryTests {
    public static final String CATEGORY_ENDPOINT = "categories/{id}";
    static Properties properties = new Properties();

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
        ;
    }

    @Test
    void getCategoryNegativeIdLettersTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, "two")
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdFractCommaTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, "7,2")
                .prettyPeek();
    }

    @Test
    void getCategoryPositiveIdExistsTest() {
        Category response = given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(200)
                .when()
                .get(CATEGORY_ENDPOINT, FOOD.getId())
                .prettyPeek()
                .body().as(Category.class);
        assertThat(response.getId(), equalTo(FOOD.getId()));
        assertThat(response.getTitle(), equalTo(FOOD.getName()));
        response.getProducts().forEach(
                e -> assertThat(e.getCategoryTitle(), equalTo(FOOD.getName()))
        );
    }

    @Test
    void getCategoryNegativeIdNegativeTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, -6)
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdEmptyTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, "")
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdFractPointTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, 4.5)
                .prettyPeek();
    }


}
