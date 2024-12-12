package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.UserSortField;
import com.fastfoodrestaraunt.backend.core.mappers.PageMapper;
import com.fastfoodrestaraunt.backend.core.mappers.UserMapper;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.UserRepository;
import com.fastfoodrestaraunt.backend.service.UserService;
import com.fastfoodrestaraunt.backend.util.PageRequestBuilder;
import com.fastfoodrestaraunt.backend.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserValidator userValidator;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<UserDto> getPageOfUsers(
            Integer offset, Integer limit, UserSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                userMapper.entityPageToDtoPage(
                        userRepository.findAll(pageRequest)));
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

        return userMapper.entityToDto(
                userRepository.save(userToUpdate));
    }

    @Override
    public void activateUser(String id) {
        User userToActivate = getUserEntity(id);

        if (Boolean.FALSE.equals(userToActivate.getActive())) {
            userToActivate.setActive(true);
            userRepository.save(userToActivate);
        }
    }

    @Override
    public void deactivateUser(String id) {
        User userToDeactivate = getUserEntity(id);

        if (Boolean.TRUE.equals(userToDeactivate.getActive())) {
            userToDeactivate.setActive(false);
            userRepository.save(userToDeactivate);
        }
    }

    @Override
    public User getUserEntity(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user with phone=%s not found", id)));
    }
}
