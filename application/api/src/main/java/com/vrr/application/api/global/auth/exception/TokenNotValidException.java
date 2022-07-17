package com.vrr.application.api.global.auth.exception;

public class TokenNotValidException extends RuntimeException{

    public TokenNotValidException() {
        super("Failed to generate Token.");
    }

    private TokenNotValidException(String message) {
        super(message);
    }
}
