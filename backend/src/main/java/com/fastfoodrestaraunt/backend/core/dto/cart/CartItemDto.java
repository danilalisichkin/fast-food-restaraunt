package com.fastfoodrestaraunt.backend.core.dto.cart;

import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;

public record CartItemDto(
        Long id,
        ProductDto product,
        Integer quantity
) {
}
