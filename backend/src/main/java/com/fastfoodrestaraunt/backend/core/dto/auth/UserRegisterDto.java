package com.fastfoodrestaraunt.backend.core.dto.auth;

public record UserRegisterDto(
        String phone,
        String firstName,
        String lastName,
        String email
) {
}
