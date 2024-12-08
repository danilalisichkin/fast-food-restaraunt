package com.fastfoodrestaraunt.backend.core.dto.cart;

public record CartItemAddingDto(
        Long productId,
        Integer quantity
) {
}
