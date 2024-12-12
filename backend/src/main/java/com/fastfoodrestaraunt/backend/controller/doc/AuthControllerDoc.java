package com.fastfoodrestaraunt.backend.controller.doc;

import com.fastfoodrestaraunt.backend.core.dto.auth.AccessTokenDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserLoginDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth API Controller", description = "Provides sign in, sign up, and refresh access token operations")
public interface AuthControllerDoc {
    @Operation(
            summary = "Sign in",
            description = "Allows the user to sign in to the application")
    ResponseEntity<AccessTokenDto> signIn(
            @Parameter(
                    name = "User credentials",
                    description = "Credentials required for signing in", required = true)
            @RequestBody @Valid UserLoginDto loginDto);

    @Operation(
            summary = "Sign up",
            description = "Allows a new user to sign up in the application")
    ResponseEntity<Void> signUp(
            @Parameter(
                    name = "User registration details",
                    description = "Details required for user registration", required = true)
            @RequestBody @Valid UserRegisterDto registerDto);

    @Operation(summary = "Refresh token", description = "Allows the user to refresh their access token using a refresh token")
    ResponseEntity<AccessTokenDto> refreshToken(
            @Parameter(
                    name = "Refresh token",
                    description = "Refresh token used to obtain a new access token", required = true)
            @RequestBody @NotEmpty String refreshToken);
}
