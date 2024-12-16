package com.fastfoodrestaraunt.backend.repository;

import com.fastfoodrestaraunt.backend.core.enums.Role;
import com.fastfoodrestaraunt.backend.entity.UserCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    Page<UserCredential> findAllByRole(Pageable pageable, Role role);

    Page<UserCredential> findAllByRoleAndActive(Pageable pageable, Role role, boolean active);
}
