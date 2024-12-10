package com.fastfoodrestaraunt.backend.core.mappers;

import com.fastfoodrestaraunt.backend.core.dto.cart.CartDto;
import com.fastfoodrestaraunt.backend.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CartItemMapper.class})
public interface CartMapper {
    CartDto entityToDto(Cart entity);
}
