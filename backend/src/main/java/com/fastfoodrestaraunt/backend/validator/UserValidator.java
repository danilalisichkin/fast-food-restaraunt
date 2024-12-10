package com.fastfoodrestaraunt.backend.validator;

import com.fastfoodrestaraunt.backend.exception.DataUniquenessConflictException;
import com.fastfoodrestaraunt.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateUserPhoneUniqueness(String phone) {
        if (userRepository.existsById(phone)) {
            throw new DataUniquenessConflictException(
                    String.format("user with phone=%s already exists", phone));
        }
    }

    public void validateUserEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DataUniquenessConflictException(
                    String.format("user with email=%s already exists", email));
        }
    }
}
