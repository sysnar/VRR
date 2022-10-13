package com.vrr.common.error.response;

import com.vrr.common.error.code.CommonErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;
    private List<CustomFieldError> errors;

    public static class CustomFieldError{
        private final String field;
        private final String value;
        private final String reason;

        private CustomFieldError(FieldError fieldError){
            this.field = fieldError.getField();
            this.value = (String) fieldError.getRejectedValue();
            this.reason = fieldError.getDefaultMessage();
        }

        public String getField() { return field; }
        public String getValue() { return value; }
        public String getReason() { return reason; }
    }

    public static ErrorResponse of(CommonErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(CommonErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .message(message == null || message.isBlank() ? errorCode.getMessage() : message)
                .build();
    }

    public static ErrorResponse of(CommonErrorCode errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .errors(bindingResult.getFieldErrors()
                        .stream()
                        .map(CustomFieldError::new)
                        .collect(Collectors.toList()))
                .build();
    }
}
