package com.vrr.application.api.global.auth.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokens {

    private AuthToken refreshToken;
    private AuthToken accessToken;
}
