package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.auth.UserLoginDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;
import com.fastfoodrestaraunt.backend.core.mappers.UserMapper;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.repository.UserRepository;
import com.fastfoodrestaraunt.backend.service.AuthService;
import com.fastfoodrestaraunt.backend.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserValidator userValidator;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public void registerUser(UserRegisterDto registerDto) {
        userValidator.validateUserPhoneUniqueness(registerDto.phone());
        userValidator.validateUserEmailUniqueness(registerDto.email());

        User newUser = userMapper.dtoToEntity(registerDto);
        newUser = userRepository.save(newUser);

        userRepository.save(newUser);
        /// TODO: implement auth, security
    }

    @Override
    public String loginUser(UserLoginDto loginDto) {
        // TODO: implement auth, security

        return "";
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        // TODO: implement auth, security

        return "";
    }
}
