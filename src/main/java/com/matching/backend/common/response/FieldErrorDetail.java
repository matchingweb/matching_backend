package com.matching.backend.common.response;

public record FieldErrorDetail(
        String field,
        String message,
        Object rejectedValue
) {
}
