package com.fastfoodrestaraunt.backend.core.dto.auth;

import com.fastfoodrestaraunt.backend.core.constant.ValidationRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to sign up new user in application")
public record UserRegisterDto(
        @NotEmpty
        @Pattern(regexp = ValidationRegex.PHONE_BELARUS_FORMAT)
        String phone,

        @NotEmpty
        @Email
        String email,

        @NotEmpty
        @Size(min = 4, max = 40)
        String password,

        @NotEmpty
        @Size(min = 2, max = 50)
        String firstName,

        @NotEmpty
        @Size(min = 2, max = 50)
        String lastName
) {
}
