package com.fastfoodrestaraunt.backend.core.dto.order;

import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Server response with item in order")
public record OrderItemDto(
        Long id,
        ProductDto product,
        Integer quantity,
        BigDecimal relevantPrice
) {
}
