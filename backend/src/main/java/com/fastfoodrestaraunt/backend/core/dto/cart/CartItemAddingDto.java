package com.fastfoodrestaraunt.backend.core.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemAddingDto(
        @NotNull
        Long productId,

        @NotNull
        @Positive
        Integer quantity
) {
}
