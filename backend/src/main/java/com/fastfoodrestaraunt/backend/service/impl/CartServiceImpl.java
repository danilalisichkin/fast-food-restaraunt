package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.cart.CartDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemDto;
import com.fastfoodrestaraunt.backend.core.mappers.CartItemMapper;
import com.fastfoodrestaraunt.backend.core.mappers.CartMapper;
import com.fastfoodrestaraunt.backend.entity.Cart;
import com.fastfoodrestaraunt.backend.entity.CartItem;
import com.fastfoodrestaraunt.backend.entity.Product;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.CartItemRepository;
import com.fastfoodrestaraunt.backend.repository.CartRepository;
import com.fastfoodrestaraunt.backend.service.CartService;
import com.fastfoodrestaraunt.backend.service.ProductService;
import com.fastfoodrestaraunt.backend.service.UserService;
import com.fastfoodrestaraunt.backend.validator.CartValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartValidator cartValidator;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductService productService;

    private final UserService userService;

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    @Override
    public CartDto getCart(String id) {
        return cartMapper.entityToDto(
                getCartEntity(id));
    }

    @Override
    public CartItemDto addItemToCart(String id, CartItemAddingDto itemAddingDto) {
        CartItem itemToAdd = cartItemMapper.dtoToEntity(itemAddingDto);
        Product product = productService.getProductEntity(itemAddingDto.productId());
        itemToAdd.setProduct(product);

        Optional<Cart> cartOptional = cartRepository.findById(id);

        if (cartOptional.isPresent()) {
            addItemToExistingCart(cartOptional.get(), itemToAdd);
        } else {
            createNewCartWithItem(id, itemToAdd);
        }

        return cartItemMapper.entityToDto(itemToAdd);
    }

    private void createNewCartWithItem(String id, CartItem cartItem) {
        cartValidator.validateCartUniqueness(id);

        User user = userService.getUserEntity(id);

        Cart cart = new Cart();
        cart.setId(user.getPhone());
        cart.setUser(user);
        cart.setItems(new ArrayList<>());

        cartItem.setCart(cart);
        cart.getItems().add(cartItem);

        cartRepository.save(cart);
    }

    private void addItemToExistingCart(Cart cart, CartItem cartItem) {
        cartValidator.validateProductNotInCart(cart, cartItem.getProduct());
        cartItem.setCart(cart);
        cart.getItems().add(cartItem);

        cartRepository.save(cart);
    }

    @Override
    public CartItemDto updateItemInCart(String id, Long productId, Integer quantity) {
        Cart cart = getCartEntity(id);
        Product product = productService.getProductEntity(productId);

        CartItem itemToUpdate = getCartItemEntity(cart, product);

        itemToUpdate.setQuantity(quantity);

        return cartItemMapper.entityToDto(
                cartItemRepository.save(itemToUpdate));
    }

    @Override
    public void deleteItemFromCart(String id, Long productId) {
        Cart cart = getCartEntity(id);
        Product product = productService.getProductEntity(productId);

        cartValidator.validateProductInCart(cart, product);

        cartItemRepository.deleteByCartAndProduct(cart, product);
    }

    @Override
    public void deleteCart(String id) {
        cartValidator.validateCartExistence(id);
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCartEntity(String id) {
        return cartRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("cart with id=%s not found", id)));
    }

    private CartItem getCartItemEntity(Cart cart, Product product) {
        return cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("product with id=%s not found in cart", product.getId())));
    }
}
