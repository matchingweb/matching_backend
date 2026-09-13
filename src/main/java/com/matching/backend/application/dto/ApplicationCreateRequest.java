package com.matching.backend.application.dto;

import jakarta.validation.constraints.Size;

public record ApplicationCreateRequest(
        @Size(max = 500, message = "지원 메시지는 500자 이하여야 합니다.")
        String message
) {
}
