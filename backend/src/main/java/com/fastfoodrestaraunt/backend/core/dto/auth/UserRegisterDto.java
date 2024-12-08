package com.fastfoodrestaraunt.backend.core.dto.auth;

import com.fastfoodrestaraunt.backend.core.constant.ValidationRegex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
        @NotEmpty
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT)
        String phone,

        @NotEmpty
        @Size(min = 2, max = 50)
        String firstName,

        @NotEmpty
        @Size(min = 2, max = 50)
        String lastName,

        @NotEmpty
        @Email
        String email
) {
}
