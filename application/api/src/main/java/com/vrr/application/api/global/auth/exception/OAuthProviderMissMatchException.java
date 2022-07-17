package com.vrr.application.api.global.auth.exception;

public class OAuthProviderMissMatchException extends RuntimeException {
    public OAuthProviderMissMatchException(String message) {
        super(message);
    }
}
