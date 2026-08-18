package com.matching.backend.auth.jwt;

public record AuthTokenPayload(
        Long userId,
        String email
) {
}
