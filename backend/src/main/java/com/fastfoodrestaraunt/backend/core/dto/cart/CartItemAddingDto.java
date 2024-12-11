package com.fastfoodrestaraunt.backend.core.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Entry to add new item to cart")
public record CartItemAddingDto(
        @NotNull
        Long productId,

        @NotNull
        @Positive
        Integer quantity
) {
}
