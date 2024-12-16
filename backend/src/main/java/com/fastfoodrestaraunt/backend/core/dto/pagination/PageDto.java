package com.fastfoodrestaraunt.backend.core.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Server response with paginated data")
public record PageDto<T>(
        Integer page,
        Integer pageSize,
        Integer totalPages,
        Long totalElements,
        List<T> content
) {
}
