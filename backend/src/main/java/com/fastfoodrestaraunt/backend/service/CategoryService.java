package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.dto.category.CategoryDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.CategorySortField;
import com.fastfoodrestaraunt.backend.entity.Category;
import org.springframework.data.domain.Sort;

public interface CategoryService {

    PageDto<CategoryDto> getPageOfCategories(
            Integer offset, Integer limit, CategorySortField sortBy, Sort.Direction sortOrder);

    CategoryDto getCategory(Long id);

    CategoryDto createCategory(String name);

    CategoryDto updateCategory(Long id, String name);

    void deleteCategory(Long id);

    Category getCategoryEntity(Long id);
}
