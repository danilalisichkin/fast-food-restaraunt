package com.fastfoodrestaraunt.backend.controller.doc;

import com.fastfoodrestaraunt.backend.core.dto.category.CategoryDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.CategorySortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@SecurityRequirement(name = "token")
@Tag(name = "Category API Controller", description = "Provides CRUD-operations with product categories")
public interface CategoryControllerDoc {

    @Operation(
            summary = "Get page of categories",
            description = "Allows to get page of product categories")
    ResponseEntity<PageDto<CategoryDto>> getAllCategories(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") CategorySortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get category",
            description = "Allows to get product category")
    ResponseEntity<CategoryDto> getCategoryById(
            @Parameter(
                    description = "Identifier of category")
            @PathVariable Long id);

    @Operation(
            summary = "Create category",
            description = "Allows to create new product category")
    ResponseEntity<CategoryDto> createCategory(
            @Parameter(
                    description = "Unique name of new category")
            @RequestBody @NotEmpty @Size(min = 2, max = 40) String name);

    @Operation(
            summary = "Update category",
            description = "Allows to update product category")
    ResponseEntity<CategoryDto> updateCategoryById(
            @Parameter(
                    description = "Identifier of category")
            @PathVariable Long id,
            @Parameter(
                    name = "Category name",
                    description = "New unique name of category that will be set", required = true)
            @RequestBody @NotEmpty @Size(min = 2, max = 40) String name);

    @Operation(
            summary = "Delete category",
            description = "Allows to delete product category")
    ResponseEntity<Void> deleteCategoryById(
            @Parameter(
                    description = "Identifier of category")
            @PathVariable Long id);
}
