package com.fastfoodrestaraunt.backend.core.dto.product;

import java.math.BigDecimal;

public record ProductAddingDto(
        String name,
        BigDecimal price,
        Integer weight,
        String imageUrl,
        String description,
        Long categoryId
) {
}
