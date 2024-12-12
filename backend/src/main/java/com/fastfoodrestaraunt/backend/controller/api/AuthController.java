package com.fastfoodrestaraunt.backend.controller.api;

import com.fastfoodrestaraunt.backend.controller.doc.AuthControllerDoc;
import com.fastfoodrestaraunt.backend.core.dto.auth.AccessTokenDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserLoginDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;
import com.fastfoodrestaraunt.backend.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDoc {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<AccessTokenDto> signIn(@RequestBody @Valid UserLoginDto loginDto) {
        AccessTokenDto token = authService.loginUser(loginDto);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserRegisterDto registerDto) {
        authService.registerUser(registerDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenDto> refreshToken(@RequestBody @NotEmpty String refreshToken) {
        AccessTokenDto token = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
