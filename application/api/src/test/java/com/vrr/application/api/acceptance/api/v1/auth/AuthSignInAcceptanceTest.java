package com.vrr.application.api.acceptance.api.v1.auth;

import com.vrr.domain.utils.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class AuthSignInAcceptanceTest extends AcceptanceTest {

    private static final String USERNAME = "SYSNAR";
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "password12#$";

    /**
     * When : User request sign up
     * Then : User Account should be created
     */
    @Test
    void Should_SignUp_With_Basic_Auth() {
        // when
        ExtractableResponse<Response> response = AuthSteps.USER_SIGNUP_REQUEST(USERNAME, EMAIL, PASSWORD);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    /**
     * When : User request sign in
     * Then : access token should be returned
     */
    @Test
    void Should_ReturnAccessToken_When_UserSignedIn() {
        // when
        AuthSteps.USER_SIGNUP_REQUEST(USERNAME, EMAIL, PASSWORD);
        ExtractableResponse<Response> response = AuthSteps.USER_SIGNIN_REQUEST(EMAIL, PASSWORD);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getString("accessToken")).isNotBlank();
    }
}
