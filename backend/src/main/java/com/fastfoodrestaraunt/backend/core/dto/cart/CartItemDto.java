package com.fastfoodrestaraunt.backend.core.dto.cart;

import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with item in shopping cart")
public record CartItemDto(
        ProductDto product,
        Integer quantity
) {
}
