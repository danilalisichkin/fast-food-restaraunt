package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.dto.order.OrderAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.Status;
import com.fastfoodrestaraunt.backend.core.enums.sort.OrderSortField;
import com.fastfoodrestaraunt.backend.entity.Order;
import org.springframework.data.domain.Sort;

public interface OrderService {

    PageDto<OrderDto> getPageOfOrders(
            Integer offset, Integer limit, OrderSortField sortBy, Sort.Direction sortOrder, Status status);

    OrderDto getOrder(Long id);

    OrderDto createOrder(OrderAddingDto addingDto);

    OrderDto updateOrderStatus(Long id, Status status);

    Order getOrderEntity(Long id);
}
