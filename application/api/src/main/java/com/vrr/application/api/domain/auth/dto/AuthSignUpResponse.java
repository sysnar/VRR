package com.vrr.application.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthSignUpResponse {

    private final String username;
    private final String email;
}
