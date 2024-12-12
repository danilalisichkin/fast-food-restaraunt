package com.fastfoodrestaraunt.backend.core.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with single error message")
public record ErrorResponse(
        String cause,
        String message
) {
}
