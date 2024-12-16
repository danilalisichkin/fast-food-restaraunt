package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.ProductSortField;
import com.fastfoodrestaraunt.backend.entity.Product;
import org.springframework.data.domain.Sort;

public interface ProductService {

    PageDto<ProductDto> getPageOfProducts(
            Integer offset, Integer limit, ProductSortField sortBy, Sort.Direction sortOrder, Long categoryId);

    ProductDto getProduct(Long id);

    ProductDto createProduct(ProductAddingDto addingDto);

    ProductDto updateProduct(Long id, ProductUpdatingDto updatingDto);

    void deleteProduct(Long id);

    Product getProductEntity(Long id);
}
