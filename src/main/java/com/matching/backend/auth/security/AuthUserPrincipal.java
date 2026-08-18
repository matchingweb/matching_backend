package com.matching.backend.auth.security;

public record AuthUserPrincipal(
        Long userId,
        String email
) {
}
