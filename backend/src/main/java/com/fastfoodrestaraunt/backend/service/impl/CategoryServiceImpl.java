package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.category.CategoryDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.CategorySortField;
import com.fastfoodrestaraunt.backend.core.mappers.CategoryMapper;
import com.fastfoodrestaraunt.backend.core.mappers.PageMapper;
import com.fastfoodrestaraunt.backend.entity.Category;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.CategoryRepository;
import com.fastfoodrestaraunt.backend.service.CategoryService;
import com.fastfoodrestaraunt.backend.util.PageRequestBuilder;
import com.fastfoodrestaraunt.backend.validator.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryValidator categoryValidator;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<CategoryDto> getPageOfCategories(
            Integer offset, Integer limit, CategorySortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                categoryMapper.entityPageToDtoPage(
                        categoryRepository.findAll(pageRequest)));
    }

    @Override
    public CategoryDto getCategory(Long id) {
        return categoryMapper.entityToDto(
                getCategoryEntity(id));
    }

    @Override
    @Transactional
    public CategoryDto createCategory(String name) {
        categoryValidator.validateCategoryNameUnique(name);

        Category newCategory = categoryMapper.dtoToEntity(name);

        return categoryMapper.entityToDto(
                categoryRepository.save(newCategory));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, String name) {
        Category categoryToUpdate = getCategoryEntity(id);

        if (!categoryToUpdate.getName().equals(name)) {
            categoryValidator.validateCategoryNameUnique(name);
            categoryToUpdate.setName(name);
        }

        return categoryMapper.entityToDto(
                categoryRepository.save(categoryToUpdate));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category categoryToDelete = getCategoryEntity(id);
        categoryRepository.delete(categoryToDelete);
    }

    @Override
    public Category getCategoryEntity(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("category with id=%s not found", id)));
    }
}
