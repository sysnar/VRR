package com.vrr.application.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AuthSignInResponse {

    private final String accessToken;

    public AuthSignInResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
