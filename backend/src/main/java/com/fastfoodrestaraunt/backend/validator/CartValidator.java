package com.fastfoodrestaraunt.backend.validator;

import com.fastfoodrestaraunt.backend.entity.Cart;
import com.fastfoodrestaraunt.backend.entity.Product;
import com.fastfoodrestaraunt.backend.exception.BadRequestException;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.CartItemRepository;
import com.fastfoodrestaraunt.backend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartValidator {
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    public void validateCartNotEmpty(Cart cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new BadRequestException(
                    String.format("cart with id=%s is empty", cart.getId()));
        }
    }

    public void validateCartUniqueness(String id) {
        if (cartRepository.existsById(id)) {
            throw new BadRequestException(
                    String.format("cart with id=%s is already exists", id));
        }
    }

    public void validateCartExistence(String id) {
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("cart with id=%s not found", id));
        }
    }

    public void validateProductNotInCart(Cart cart, Product product) {
        if (cartItemRepository.existsByCartAndProduct(cart, product)) {
            throw new BadRequestException(
                    String.format("product with id=%s already in cart", product.getId()));
        }
    }

    public void validateProductInCart(Cart cart, Product product) {
        if (!cartItemRepository.existsByCartAndProduct(cart, product)) {
            throw new ResourceNotFoundException(
                    String.format("product with id=%s is not in cart", product.getId()));
        }
    }
}
