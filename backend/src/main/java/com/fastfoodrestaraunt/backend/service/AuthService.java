package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.dto.auth.AccessTokenDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserLoginDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;

public interface AuthService {

    void registerUser(UserRegisterDto registerDto);

    AccessTokenDto loginUser(UserLoginDto loginDto);

    AccessTokenDto refreshAccessToken(String refreshToken);
}
