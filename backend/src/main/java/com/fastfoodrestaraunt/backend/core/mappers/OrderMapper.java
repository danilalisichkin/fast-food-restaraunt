package com.fastfoodrestaraunt.backend.core.mappers;

import com.fastfoodrestaraunt.backend.core.dto.order.OrderAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderDto;
import com.fastfoodrestaraunt.backend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "userPhone", source = "user.phone")
    OrderDto entityToDto(Order entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    Order dtoToEntity(OrderAddingDto dto);

    List<OrderDto> entityListToDtoList(List<Order> enitityList);

    default Page<OrderDto> entityPageToDtoPage(Page<Order> entityPage) {
        List<OrderDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
