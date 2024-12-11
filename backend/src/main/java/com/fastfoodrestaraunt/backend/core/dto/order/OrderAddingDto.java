package com.fastfoodrestaraunt.backend.core.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Entry to add new order")
public record OrderAddingDto(
        @NotEmpty
        String cartId,

        @NotEmpty
        String deliveryAddress
) {
}
