package com.fastfoodrestaraunt.backend.core.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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
