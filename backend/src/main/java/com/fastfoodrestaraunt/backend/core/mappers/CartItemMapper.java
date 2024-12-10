package com.fastfoodrestaraunt.backend.core.mappers;

import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemDto;
import com.fastfoodrestaraunt.backend.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProductMapper.class})
public interface CartItemMapper {
    CartItemDto entityToDto(CartItem entity);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem dtoToEntity(CartItemAddingDto dto);

    void updateEntityFromDto(Integer quantity, @MappingTarget CartItem entity);

    List<CartItemDto> entityListToDtoList(List<CartItem> enitityList);

    default Page<CartItemDto> entityPageToDtoPage(Page<CartItem> entityPage) {
        List<CartItemDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
