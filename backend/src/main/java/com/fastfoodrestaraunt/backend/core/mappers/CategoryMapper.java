package com.fastfoodrestaraunt.backend.core.mappers;

import com.fastfoodrestaraunt.backend.core.dto.category.CategoryDto;
import com.fastfoodrestaraunt.backend.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryDto entityToDto(Category entity);

    Category dtoToEntity(String name);

    void updateEntityFromDto(String name, @MappingTarget Category entity);

    List<CategoryDto> entityListToDtoList(List<Category> enitityList);

    default Page<CategoryDto> entityPageToDtoPage(Page<Category> entityPage) {
        List<CategoryDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
