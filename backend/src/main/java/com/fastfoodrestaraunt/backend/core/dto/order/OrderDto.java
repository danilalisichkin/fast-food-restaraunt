package com.fastfoodrestaraunt.backend.core.dto.order;

import com.fastfoodrestaraunt.backend.core.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Server response with order")
public record OrderDto(
        Long id,
        String userPhone,
        String deliveryAddress,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        List<OrderItemDto> items
) {
}
