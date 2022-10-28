package com.vrr.application.api.global.libs;

import com.vrr.application.api.global.auth.token.AuthTokenProvider;
import com.vrr.common.code.auth.RoleType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AccessTokenUtils {

    private final static AuthTokenProvider tokenProvider = new AuthTokenProvider("V33a0A1Vd081hjb3w43DSp667HK1ifVd081hjb3w33a0A1Vd081h");

    public static String createToken() {
        String role = "[" + RoleType.USER.getCode() + "]";
        LocalDateTime afterAHour = LocalDateTime.now().plusHours(1);
        Timestamp expireDate = Timestamp.valueOf(afterAHour);
        return tokenProvider.createAuthToken("anonymousUser", role, expireDate).getToken();
    }
}
