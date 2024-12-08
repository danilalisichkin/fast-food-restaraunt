package com.fastfoodrestaraunt.backend.core.dto.pagination;

import java.util.List;

public record PageDto<T>(
        Integer page,
        Integer pageSize,
        Integer totalPages,
        List<T> content
) {
}
