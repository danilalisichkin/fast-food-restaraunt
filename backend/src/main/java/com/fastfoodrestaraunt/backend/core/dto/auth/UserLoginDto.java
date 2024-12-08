package com.fastfoodrestaraunt.backend.core.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserLoginDto(
        @NotNull
        String identifier,

        @NotNull
        @Size(min = 4, max = 40)
        String password
) {
}
