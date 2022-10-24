package com.vrr.application.api.acceptance.api.v1.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;

public class AuthSteps {

    public static final String USERNAME = "SYSNAR";
    public static final String EMAIL = "test@email.com";
    public static final String PASSWORD = "password12#$";

    public static String USER_HAVE_SINGED_IN(String username, String email, String password) {
        USER_SIGNUP_REQUEST(username, email, password);
        ExtractableResponse<Response> response = USER_SIGNIN_REQUEST(email, password);
        return response.jsonPath().getString("accessToken");
    }

    public static ExtractableResponse<Response> USER_SIGNUP_REQUEST(String username, String email, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/auth/signup")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    public static ExtractableResponse<Response> USER_SIGNIN_REQUEST(String email, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/auth/signin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
