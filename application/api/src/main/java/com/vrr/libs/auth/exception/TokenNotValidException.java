package com.vrr.libs.auth.exception;

public class TokenNotValidException extends RuntimeException{

    public TokenNotValidException() {
        super("Failed to generate Token.");
    }

    private TokenNotValidException(String message) {
        super(message);
    }
}
