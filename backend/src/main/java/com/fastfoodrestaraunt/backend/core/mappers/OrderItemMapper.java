package com.fastfoodrestaraunt.backend.core.mappers;

import com.fastfoodrestaraunt.backend.core.dto.order.OrderItemAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderItemDto;
import com.fastfoodrestaraunt.backend.entity.CartItem;
import com.fastfoodrestaraunt.backend.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProductMapper.class})
public interface OrderItemMapper {
    OrderItemDto entityToDto(OrderItem entity);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem dtoToEntity(OrderItemAddingDto dto);

    @Mapping(target = "relevantPrice", source = "product.price")
    @Mapping(target = "order", ignore = true)
    OrderItem cartItemToEntity(CartItem cartItem);

    List<OrderItem> cartItemListToEntityList(List<CartItem> cartItemList);

    List<OrderItemDto> entityListToDtoList(List<OrderItem> enitityList);

    default Page<OrderItemDto> entityPageToDtoPage(Page<OrderItem> entityPage) {
        List<OrderItemDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
