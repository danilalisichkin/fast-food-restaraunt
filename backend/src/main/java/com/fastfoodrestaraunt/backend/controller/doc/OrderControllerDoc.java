package com.fastfoodrestaraunt.backend.controller.doc;

import com.fastfoodrestaraunt.backend.core.dto.order.OrderAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.Status;
import com.fastfoodrestaraunt.backend.core.enums.sort.OrderSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@SecurityRequirement(name = "token")
@Tag(name = "Order API Controller", description = "Provides CRUD-operations with orders")
public interface OrderControllerDoc {

    @Operation(
            summary = "Get page of orders",
            description = "Allows to get page of orders, can be sorted using status or not")
    ResponseEntity<PageDto<OrderDto>> getAllOrders(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") OrderSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) Status status);

    @Operation(
            summary = "Get order",
            description = "Allows to get order")
    ResponseEntity<OrderDto> getOrderById(
            @Parameter(
                    description = "Identifier of order")
            @PathVariable Long id);

    @Operation(
            summary = "Create order",
            description = "Allows to create new order")
    ResponseEntity<OrderDto> createOrder(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new order", required = true)
            @RequestBody @Valid OrderAddingDto addingDto);

    @Operation(
            summary = "Update order status",
            description = "Allows to update order status")
    ResponseEntity<OrderDto> updateOrderStatus(
            @Parameter(
                    description = "Identifier of order")
            @PathVariable Long id,
            @Parameter(
                    name = "Order status",
                    description = "New order status that will be set", required = true)
            @RequestBody @NotNull Status status);
}
