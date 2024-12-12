package com.fastfoodrestaraunt.backend.core.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Entry to add new item to order")
public record OrderItemAddingDto(
        @NotNull
        Long productId,

        @NotNull
        @Positive
        Integer quantity
) {
}
