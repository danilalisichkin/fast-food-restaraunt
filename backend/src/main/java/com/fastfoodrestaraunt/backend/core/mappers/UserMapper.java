package com.fastfoodrestaraunt.backend.core.mappers;

import com.fastfoodrestaraunt.backend.core.dto.auth.UserRegisterDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserUpdatingDto;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.entity.UserCredential;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto entityToDto(User entity);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    UserDto credentialToDto(UserCredential credential);

    User dtoToEntity(UserRegisterDto dto);

    @Mapping(target = "password", ignore = true)
    UserCredential dtoToCredential(UserRegisterDto dto);

    void updateEntityFromDto(UserUpdatingDto dto, @MappingTarget User entity);

    List<UserDto> entityListToDtoList(List<User> enitityList);

    List<UserDto> credentialListToDtoList(List<UserCredential> credentialList);

    default Page<UserDto> entityPageToDtoPage(Page<User> entityPage) {
        List<UserDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    default Page<UserDto> credentialPageToDtoPage(Page<UserCredential> credentialPage) {
        List<UserDto> dtoList = credentialListToDtoList(credentialPage.getContent());
        return new PageImpl<>(dtoList, credentialPage.getPageable(), credentialPage.getTotalElements());
    }
}
