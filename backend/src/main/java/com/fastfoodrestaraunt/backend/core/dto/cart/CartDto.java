package com.fastfoodrestaraunt.backend.core.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Server response with user's shopping cart")
public record CartDto(
        List<CartItemDto> items
) {
}
