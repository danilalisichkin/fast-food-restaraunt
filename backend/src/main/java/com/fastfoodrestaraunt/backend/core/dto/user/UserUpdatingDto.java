package com.fastfoodrestaraunt.backend.core.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to update existing user")
public record UserUpdatingDto(
        @NotEmpty
        @Size(min = 2, max = 50)
        String firstName,

        @NotEmpty
        @Size(min = 2, max = 50)
        String lastName,

        @NotEmpty
        @Email
        String email,

        @NotEmpty
        Boolean active
) {
}
