package com.fastfoodrestaraunt.backend.core.dto.auth;

public record UserLoginDto(
        String identifier,
        String password
) {
}
