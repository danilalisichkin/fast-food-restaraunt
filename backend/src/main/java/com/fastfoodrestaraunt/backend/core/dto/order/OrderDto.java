package com.fastfoodrestaraunt.backend.core.dto.order;

import com.fastfoodrestaraunt.backend.core.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        String deliveryAddress,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        List<OrderItemDto> items
) {
}
