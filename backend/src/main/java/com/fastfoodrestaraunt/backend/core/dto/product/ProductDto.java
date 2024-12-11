package com.fastfoodrestaraunt.backend.core.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Server response with product")
public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Integer weight,
        String imageUrl,
        String description,
        Long categoryId
) {
}
