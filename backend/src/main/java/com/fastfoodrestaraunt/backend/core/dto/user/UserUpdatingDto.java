package com.fastfoodrestaraunt.backend.core.dto.user;

public record UserUpdatingDto(
        String firstName,
        String lastName,
        String email,
        Boolean active
) {
}
