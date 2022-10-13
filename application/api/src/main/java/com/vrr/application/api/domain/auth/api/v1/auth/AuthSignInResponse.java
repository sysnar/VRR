package com.vrr.application.api.domain.auth.api.v1.auth;

import lombok.Getter;

@Getter
public class AuthSignInResponse {

    private final String accessToken;

    public AuthSignInResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
