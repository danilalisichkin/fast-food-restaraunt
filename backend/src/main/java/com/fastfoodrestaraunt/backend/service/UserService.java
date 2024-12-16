package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.UserSortField;
import com.fastfoodrestaraunt.backend.entity.User;
import org.springframework.data.domain.Sort;

public interface UserService {

    PageDto<UserDto> getPageOfUsers(
            Integer offset, Integer limit, UserSortField sortBy, Sort.Direction sortOrder, Boolean active);

    UserDto getUser(String id);

    UserDto updateUser(String id, UserUpdatingDto updatingDto);

    void activateUser(String id);

    void deactivateUser(String id);

    User getUserEntity(String id);

    User getUserEntityByIdentifier(String identifier);
}
