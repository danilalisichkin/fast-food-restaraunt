package com.fastfoodrestaraunt.backend.controller.api;

import com.fastfoodrestaraunt.backend.controller.doc.OrderControllerDoc;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.Status;
import com.fastfoodrestaraunt.backend.core.enums.sort.OrderSortField;
import com.fastfoodrestaraunt.backend.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController implements OrderControllerDoc {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<PageDto<OrderDto>> getAllOrders(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") OrderSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) Status status) {

        PageDto<OrderDto> page = orderService.getPageOfOrders(offset, limit, sortBy, sortOrder, status);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrder(id);

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderAddingDto addingDto) {
        OrderDto order = orderService.createOrder(addingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id, @RequestBody @NotNull Status status) {
        OrderDto order = orderService.updateOrderStatus(id, status);

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}

