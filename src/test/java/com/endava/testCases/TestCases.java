package com.endava.testCases;

import com.endava.models.User;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.HTML;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class TestCases {

    private Faker faker = new Faker();

    @Test
    public void getUsers(){
        //get all users
        given(). baseUri("https://graph.facebook.com")
                .auth().oauth2("455136538609782|4fdbc3c6f529deaf84edc82aee315790")
                .pathParam("app-id","455136538609782")
                .log().all()
                .get("/v8.0/{app-id}/accounts/test-users")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void postUser(){
        //create user
        User user = new User();
        user.setName("Mihaela Ionescu");
        user.setPassword("RestAssured2020");
//        user.setEmail("mihaela10ionescu@yahoo.com");
//        user.setPassword("RestAssured2020");
//        user.setId(faker.number().digits(15));
//        user.setLogin_url("https://developers.facebook.com/checkpoint/test-user-login/"+user.getId()+"/?is_sandbox=0&sandbox_asset_type=test_user&page_id=0");


        //post
        ValidatableResponse resp = given(). baseUri("https://graph.facebook.com")
                .auth().oauth2("455136538609782|4fdbc3c6f529deaf84edc82aee315790")
                .pathParam("app-id","455136538609782")
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .post("/v8.0/{app-id}/accounts/test-users")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);

        String id = resp.extract().jsonPath().getString("id");
        String name = resp.extract().jsonPath().getString("name");

        System.out.println(name + "   " + id);


    }

    @Test
    public void getUserById(){

        given(). baseUri("https://graph.facebook.com")
                .auth().oauth2("455136538609782|4fdbc3c6f529deaf84edc82aee315790")
                .pathParam("test-user-id","226580715340533")
                .log().all()
                .get("/v8.0/{test-user-id}")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void putUserById(){
        User user = new User();

        user.setName("Mimi Ionescu");
        user.setPassword("RestAssured2020");

        given(). baseUri("https://graph.facebook.com")
                .auth().oauth2("455136538609782|4fdbc3c6f529deaf84edc82aee315790")
                .pathParam("test-user-id","226580715340533")
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .put("/v8.0/{test-user-id}")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);
    }

}
