package com.fastfoodrestaraunt.backend.core.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemAddingDto(
        @NotNull
        Long productId,

        @NotNull
        @Positive
        Integer quantity
) {
}
