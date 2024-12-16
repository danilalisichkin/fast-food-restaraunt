package com.fastfoodrestaraunt.backend.core.mappers;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public <T> PageDto<T> pageToPageDto(Page<T> page) {
        return new PageDto<>(
                page.getPageable().getPageNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent()
        );
    }
}
