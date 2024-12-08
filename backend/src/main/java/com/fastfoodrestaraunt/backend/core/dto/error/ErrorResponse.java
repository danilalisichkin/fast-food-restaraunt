package com.fastfoodrestaraunt.backend.core.dto.error;

public record ErrorResponse(
        String cause,
        String message
) {
}
