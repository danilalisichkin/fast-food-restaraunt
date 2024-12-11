package com.fastfoodrestaraunt.backend.core.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with user")
public record UserDto(
        String phone,
        String firstName,
        String lastName,
        String email,
        Boolean active
) {
}
