package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.auth.AccessTokenDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserLoginDto;
import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;
import com.fastfoodrestaraunt.backend.core.enums.Role;
import com.fastfoodrestaraunt.backend.core.mappers.UserMapper;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.entity.UserCredential;
import com.fastfoodrestaraunt.backend.exception.InternalErrorException;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.exception.UnauthorizedException;
import com.fastfoodrestaraunt.backend.repository.UserCredentialRepository;
import com.fastfoodrestaraunt.backend.repository.UserRepository;
import com.fastfoodrestaraunt.backend.security.config.JwtConfig;
import com.fastfoodrestaraunt.backend.security.utils.JwtUtils;
import com.fastfoodrestaraunt.backend.service.AuthService;
import com.fastfoodrestaraunt.backend.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

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

        createUser(registerDto);
        createUserCredential(registerDto);
    }

    @Override
    public AccessTokenDto loginUser(UserLoginDto loginDto) {
        UserCredential userCredential = getUserCredentialByIdentifier(loginDto.identifier());

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
        UserCredential userCredential = getUserCredentialByIdentifier(identifier);

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

    public void setUserActive(String id, boolean active) {
        UserCredential userCredential = getUserCredentialById(id);

        userCredential.setActive(active);
        userCredentialRepository.save(userCredential);
    }

    private void createUser(UserRegisterDto registerDto) {
        User newUser = userMapper.dtoToEntity(registerDto);
        newUser.setActive(true);

        userRepository.save(newUser);
    }

    private void createUserCredential(UserRegisterDto registerDto) {
        UserCredential newUserCredential = userMapper.dtoToCredential(registerDto);

        newUserCredential.setRole(Role.CUSTOMER);
        newUserCredential.setActive(true);
        newUserCredential.setPassword(passwordEncoder.encode(registerDto.password()));

        userCredentialRepository.save(newUserCredential);
    }

    private UserCredential getUserCredentialByIdentifier(String identifier) {
        return userCredentialRepository.findById(identifier)
                .or(() -> userCredentialRepository.findByEmail(identifier))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user credential with identifier %s not found", identifier)));
    }

    private UserCredential getUserCredentialById(String id) {
        return userCredentialRepository
                .findById(id)
                .orElseThrow(() -> new InternalErrorException(
                        String.format("user credential with id %s not found", id)));
    }
}
