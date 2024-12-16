package com.fastfoodrestaraunt.backend.controller.doc;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.ProductSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@SecurityRequirement(name = "token")
@Tag(name = "Product API Controller", description = "Provides CRUD-operations with products")
public interface ProductControllerDoc {

    @Operation(
            summary = "Get page of products",
            description = "Allows to get page of products")
    ResponseEntity<PageDto<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") ProductSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) Long categoryId);

    @Operation(
            summary = "Get product",
            description = "Allows to get product")
    ResponseEntity<ProductDto> getProductById(
            @Parameter(
                    description = "Identifier of product")
            @PathVariable Long id);

    @Operation(
            summary = "Create product",
            description = "Allows to create new product")
    ResponseEntity<ProductDto> createProduct(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new product", required = true)
            @RequestBody @Valid ProductAddingDto addingDto);

    @Operation(
            summary = "Update product",
            description = "Allows to update product")
    ResponseEntity<ProductDto> updateProductById(
            @Parameter(
                    description = "Identifier of product")
            @PathVariable Long id,
            @Parameter(
                    name = "Required data",
                    description = "Product data that will be changed", required = true)
            @RequestBody @Valid ProductUpdatingDto updatingDto);

    @Operation(
            summary = "Delete product",
            description = "Allows to delete product")
    ResponseEntity<Void> deleteProductById(
            @Parameter(
                    description = "Identifier of product")
            @PathVariable Long id);
}
