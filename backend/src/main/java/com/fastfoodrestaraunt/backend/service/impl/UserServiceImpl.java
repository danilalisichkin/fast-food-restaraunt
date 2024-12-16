package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.Role;
import com.fastfoodrestaraunt.backend.core.enums.sort.UserSortField;
import com.fastfoodrestaraunt.backend.core.mappers.PageMapper;
import com.fastfoodrestaraunt.backend.core.mappers.UserMapper;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.entity.UserCredential;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.UserRepository;
import com.fastfoodrestaraunt.backend.service.UserCredentialService;
import com.fastfoodrestaraunt.backend.service.UserService;
import com.fastfoodrestaraunt.backend.util.PageRequestBuilder;
import com.fastfoodrestaraunt.backend.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialService userCredentialService;

    private final UserValidator userValidator;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<UserDto> getPageOfUsers(
            Integer offset, Integer limit, UserSortField sortBy, Sort.Direction sortOrder, Boolean active) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        Page<UserCredential> users = userCredentialService.getAllByRoleAndActive(pageRequest, Role.CUSTOMER, active);

        return pageMapper.pageToPageDto(
                userMapper.credentialPageToDtoPage(users));
    }

    @Override
    public UserDto getUser(String id) {
        return userMapper.entityToDto(
                getUserEntity(id));
    }

    @Override
    @Transactional
    public UserDto updateUser(String id, UserUpdatingDto updatingDto) {
        User userToUpdate = getUserEntity(id);

        if (!userToUpdate.getEmail().equals(updatingDto.email())) {
            userValidator.validateUserEmailUniqueness(updatingDto.email());
        }

        userMapper.updateEntityFromDto(updatingDto, userToUpdate);
        userCredentialService.setUserActive(userToUpdate.getUserCredential(), updatingDto.active());

        return userMapper.entityToDto(
                userRepository.save(userToUpdate));
    }

    @Override
    public void activateUser(String id) {
        User userToActivate = getUserEntity(id);
        UserCredential userCredential = userToActivate.getUserCredential();

        if (Boolean.FALSE.equals(userCredential.getActive())) {
            userCredentialService.setUserActive(userCredential, true);
            userRepository.save(userToActivate);
        }
    }

    @Override
    public void deactivateUser(String id) {
        User userToActivate = getUserEntity(id);
        UserCredential userCredential = userToActivate.getUserCredential();

        if (Boolean.TRUE.equals(userCredential.getActive())) {
            userCredentialService.setUserActive(userCredential, false);
            userRepository.save(userToActivate);
        }
    }

    public User getUserEntity(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user with phone=%s not found", id)));
    }

    public User getUserEntityByIdentifier(String identifier) {
        return userRepository.findById(identifier)
                .or(() -> userRepository.findByEmail(identifier))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user with identifier %s not found", identifier)));
    }
}
