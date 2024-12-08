package com.fastfoodrestaraunt.backend.core.dto.auth;

public record AccessTokenDto(
        String accessToken,
        long expiresIn,
        String refreshToken,
        long refreshExpiresIn,
        String tokenType
) {
}
