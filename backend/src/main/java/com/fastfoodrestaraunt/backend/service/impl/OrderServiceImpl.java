package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.order.OrderAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.Status;
import com.fastfoodrestaraunt.backend.core.enums.sort.OrderSortField;
import com.fastfoodrestaraunt.backend.core.mappers.OrderItemMapper;
import com.fastfoodrestaraunt.backend.core.mappers.OrderMapper;
import com.fastfoodrestaraunt.backend.core.mappers.PageMapper;
import com.fastfoodrestaraunt.backend.entity.Cart;
import com.fastfoodrestaraunt.backend.entity.CartItem;
import com.fastfoodrestaraunt.backend.entity.Order;
import com.fastfoodrestaraunt.backend.entity.OrderItem;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.OrderRepository;
import com.fastfoodrestaraunt.backend.service.CartService;
import com.fastfoodrestaraunt.backend.service.OrderService;
import com.fastfoodrestaraunt.backend.util.PageRequestBuilder;
import com.fastfoodrestaraunt.backend.validator.CartValidator;
import com.fastfoodrestaraunt.backend.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderValidator orderValidator;

    private final CartValidator cartValidator;

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<OrderDto> getPageOfOrders(
            Integer offset, Integer limit, OrderSortField sortBy, Sort.Direction sortOrder, Status status) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                orderMapper.entityPageToDtoPage(
                        orderRepository.findAllByStatus(pageRequest, status)));
    }

    @Override
    public OrderDto getOrder(Long id) {
        return orderMapper.entityToDto(
                getOrderEntity(id));
    }

    @Override
    public OrderDto createOrder(OrderAddingDto addingDto) {
        Cart cart = cartService.getCartEntity(addingDto.cartId());

        cartValidator.validateCartNotEmpty(cart);

        Order newOrder = orderMapper.dtoToEntity(addingDto);

        User user = cart.getUser();
        newOrder.setUser(user);

        List<CartItem> cartItems = cart.getItems();
        List<OrderItem> orderItems = orderItemMapper.cartItemListToEntityList(cartItems);
        orderItems.forEach(orderItem -> orderItem.setOrder(newOrder));
        newOrder.setItems(orderItems);

        newOrder.setStatus(Status.NEW);

        return orderMapper.entityToDto(
                orderRepository.save(newOrder));
    }

    @Override
    public OrderDto updateOrderStatus(Long id, Status status) {
        Order orderToUpdate = getOrderEntity(id);

        orderValidator.validateOrderStatusChanging(orderToUpdate, status);

        orderToUpdate.setStatus(status);
        if (status == Status.DELIVERED) {
            orderToUpdate.setCompletedAt(LocalDateTime.now());
        }

        return orderMapper.entityToDto(
                orderRepository.save(orderToUpdate));
    }

    @Override
    public Order getOrderEntity(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("order with id=%s not found", id)));
    }
}
