package com.fastfoodrestaraunt.backend.core.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to sign in existing user in application")
public record UserLoginDto(
        @NotNull
        String identifier,

        @NotNull
        @Size(min = 4, max = 40)
        String password
) {
}
