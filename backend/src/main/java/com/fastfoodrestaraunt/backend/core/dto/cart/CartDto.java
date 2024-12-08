package com.fastfoodrestaraunt.backend.core.dto.cart;

import java.util.List;

public record CartDto(
        List<CartItemDto> items
) {
}
