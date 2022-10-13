package com.vrr.common.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // COMMON HTTP ERROR
    INTERNAL_SERVER_ERROR(500, "C000", "Internal server error."),
    INVALID_PARAMETER(400, "C001", "Invalid parameter include."),
    NOT_READABLE_PARAMETER(400, "C002", "Cannot deserialize value. Such as string to array."),
    METHOD_NOT_ALLOWED(405, "C010", "Method not allowed."),

    // ILLEGAL ACTION ERROR
    ILLEGAL_STAGE(400, "I001", "Illegal state."),
    ILLEGAL_ARGUMENT(400, "I002", "Illegal argument."),

    // AUTHENTICATION ERROR
    UNAUTHENTICATED(401, "C005", "Fail to authentication."),
    UNAUTHORIZED(403, "C006", "Authorization is required to access this resource."),
    ;

    private final int httpStatus;
    private final String code;
    private final String message;

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
