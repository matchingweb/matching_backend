package com.matching.backend.common.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        String code,
        String message,
        List<FieldErrorDetail> fieldErrors
) {

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, List.of());
    }

    public static ErrorResponse of(String code, String message, List<FieldErrorDetail> fieldErrors) {
        return new ErrorResponse(code, message, fieldErrors);
    }
}
