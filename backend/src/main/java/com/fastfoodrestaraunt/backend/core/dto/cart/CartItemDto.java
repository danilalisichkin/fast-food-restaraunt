package com.fastfoodrestaraunt.backend.core.dto.cart;

import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;

public record CartItemDto(
        ProductDto product,
        Integer quantity
) {
}
