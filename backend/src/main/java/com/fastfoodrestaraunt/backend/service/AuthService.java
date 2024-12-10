package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.dto.auth.UserLoginDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;

public interface AuthService {

    void registerUser(UserRegisterDto registerDto);

    String loginUser(UserLoginDto loginDto);

    String refreshAccessToken(String refreshToken);
}
