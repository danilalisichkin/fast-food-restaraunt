package com.fastfoodrestaraunt.backend.core.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with user's access and refresh tokens")
public record AccessTokenDto(
        String accessToken,
        long expiresIn,
        String refreshToken,
        long refreshExpiresIn,
        String tokenType
) {
}
