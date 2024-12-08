package com.fastfoodrestaraunt.backend.core.dto.order;

public record OrderItemAddingDto(
        Long productId,
        Integer quantity
) {
}
