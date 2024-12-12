package com.fastfoodrestaraunt.backend.service;

import com.fastfoodrestaraunt.backend.core.dto.cart.CartDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemDto;
import com.fastfoodrestaraunt.backend.entity.Cart;

public interface CartService {

    CartDto getCart(String id);

    CartItemDto addItemToCart(String id, CartItemAddingDto itemAddingDto);

    CartItemDto updateItemInCart(String id, Long productId, Integer quantity);

    void deleteItemFromCart(String id, Long productId);

    void deleteCart(String id);

    Cart getCartEntity(String id);
}
