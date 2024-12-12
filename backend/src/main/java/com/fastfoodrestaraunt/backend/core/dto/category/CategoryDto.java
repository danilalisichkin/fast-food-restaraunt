package com.fastfoodrestaraunt.backend.core.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with product category")
public record CategoryDto(
        Long id,
        String name
) {
}
