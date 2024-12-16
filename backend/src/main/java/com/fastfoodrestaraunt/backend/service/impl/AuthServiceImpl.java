package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.auth.AccessTokenDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserLoginDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;
import com.fastfoodrestaraunt.backend.core.enums.Role;
import com.fastfoodrestaraunt.backend.core.mappers.UserMapper;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.entity.UserCredential;
import com.fastfoodrestaraunt.backend.exception.UnauthorizedException;
import com.fastfoodrestaraunt.backend.repository.UserCredentialRepository;
import com.fastfoodrestaraunt.backend.repository.UserRepository;
import com.fastfoodrestaraunt.backend.security.config.JwtConfig;
import com.fastfoodrestaraunt.backend.security.utils.JwtUtils;
import com.fastfoodrestaraunt.backend.service.AuthService;
import com.fastfoodrestaraunt.backend.service.MailService;
import com.fastfoodrestaraunt.backend.service.UserService;
import com.fastfoodrestaraunt.backend.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final MailService mailService;

    private final UserValidator userValidator;

    private final UserRepository userRepository;

    private final UserCredentialRepository userCredentialRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final JwtConfig jwtConfig;

    @Override
    public void registerUser(UserRegisterDto registerDto) {
        userValidator.validateUserPhoneUniqueness(registerDto.phone());
        userValidator.validateUserEmailUniqueness(registerDto.email());

        User newUser = userMapper.dtoToEntity(registerDto);

        UserCredential newUserCredential = userMapper.dtoToCredential(registerDto);
        newUserCredential.setPhone(registerDto.phone());
        newUserCredential.setRole(Role.CUSTOMER);
        newUserCredential.setActive(true);
        newUserCredential.setPassword(passwordEncoder.encode(registerDto.password()));
        newUserCredential.setUser(newUser);

        newUser.setUserCredential(newUserCredential);

        userRepository.save(newUser);

        mailService.sendGreetingMessage(registerDto.email());
    }

    @Override
    public AccessTokenDto loginUser(UserLoginDto loginDto) {
        User user = userService.getUserEntityByIdentifier(loginDto.identifier());
        UserCredential userCredential = user.getUserCredential();

        if (!passwordEncoder.matches(loginDto.password(), userCredential.getPassword())) {
            throw new UnauthorizedException("invalid username or password");
        }

        String accessToken = jwtUtils.generateAccessToken(userCredential);
        String refreshToken = jwtUtils.generateRefreshToken(userCredential);

        return new AccessTokenDto(
                accessToken,
                Long.parseLong(jwtConfig.getAccessExpiration()),
                refreshToken,
                Long.parseLong(jwtConfig.getRefreshExpiration()),
                "Bearer"
        );
    }

    @Override
    public AccessTokenDto refreshAccessToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new UnauthorizedException("invalid refresh token");
        }

        String identifier = jwtUtils.extractSubject(refreshToken);
        User user = userService.getUserEntity(identifier);
        UserCredential userCredential = user.getUserCredential();

        String newAccessToken = jwtUtils.generateAccessToken(userCredential);
        String newRefreshToken = jwtUtils.generateRefreshToken(userCredential);

        return new AccessTokenDto(
                newAccessToken,
                Long.parseLong(jwtConfig.getAccessExpiration()),
                newRefreshToken,
                Long.parseLong(jwtConfig.getRefreshExpiration()),
                "Bearer"
        );
    }
}