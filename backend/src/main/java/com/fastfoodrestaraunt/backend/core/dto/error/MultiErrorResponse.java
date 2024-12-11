package com.fastfoodrestaraunt.backend.core.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Server response with multiple error messages")
public record MultiErrorResponse(
        String cause,
        Map<String, List<String>> messages
) {
}
