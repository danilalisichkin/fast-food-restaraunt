package com.fastfoodrestaraunt.backend.core.dto.order;

import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        ProductDto product,
        Integer quantity,
        BigDecimal relevantPrice
) {
}
