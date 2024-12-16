package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.enums.Role;
import com.fastfoodrestaraunt.backend.entity.UserCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserCredentialService {
    void setUserActive(UserCredential userCredential, boolean active);

    Page<UserCredential> getAllByRoleAndActive(PageRequest pageRequest, Role role, Boolean active);
}
