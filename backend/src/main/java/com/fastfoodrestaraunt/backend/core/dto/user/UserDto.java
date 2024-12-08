package com.fastfoodrestaraunt.backend.core.dto.user;

public record UserDto(
        String phone,
        String firstName,
        String lastName,
        String email,
        Boolean active
) {
}
