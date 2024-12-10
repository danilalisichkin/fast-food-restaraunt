package com.fastfoodrestaraunt.backend.core.dto.order;

import jakarta.validation.constraints.NotEmpty;

public record OrderAddingDto(
        @NotEmpty
        String cartId,

        @NotEmpty
        String deliveryAddress
) {
}
