package com.vrr.common.error.code;

public interface ErrorCode {

    String name();

    int getHttpStatus();

    String getCode();

    String getMessage();
}
