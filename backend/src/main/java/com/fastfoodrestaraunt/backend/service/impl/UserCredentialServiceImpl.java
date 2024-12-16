package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.enums.Role;
import com.fastfoodrestaraunt.backend.entity.UserCredential;
import com.fastfoodrestaraunt.backend.repository.UserCredentialRepository;
import com.fastfoodrestaraunt.backend.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public void setUserActive(UserCredential userCredential, boolean active) {
        userCredential.setActive(active);
        userCredentialRepository.save(userCredential);
    }

    @Override
    public Page<UserCredential> getAllByRoleAndActive(PageRequest pageRequest, Role role, Boolean active) {
        return active != null
                ? userCredentialRepository.findAllByRoleAndActive(pageRequest, role, active)
                : userCredentialRepository.findAllByRole(pageRequest, role);
    }
}
