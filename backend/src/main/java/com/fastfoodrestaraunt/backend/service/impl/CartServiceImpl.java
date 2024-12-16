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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
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
    @Transactional
    public CartItemDto addItemToCart(String id, CartItemAddingDto itemAddingDto) {
        cartValidator.validateUserIsCartOwner(id);

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
        cartValidator.validateUserIsCartOwner(id);
        cartValidator.validateCartUniqueness(id);

        User user = userService.getUserEntity(id);

        Cart cart = new Cart();
        cart.setId(user.getPhone());
        cart.setUser(user);
        cart.setItems(new ArrayList<>());

        cartItem.setCart(cart);
        cart.getItems().add(cartItem);

        setCartUpdatedTime(cart);

        cartRepository.save(cart);
    }

    private void addItemToExistingCart(Cart cart, CartItem cartItem) {
        cartValidator.validateProductNotInCart(cart, cartItem.getProduct());
        cartItem.setCart(cart);
        cart.getItems().add(cartItem);

        setCartUpdatedTime(cart);

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartItemDto updateItemInCart(String id, Long productId, Integer quantity) {
        cartValidator.validateUserIsCartOwner(id);

        Cart cart = getCartEntity(id);
        Product product = productService.getProductEntity(productId);

        CartItem itemToUpdate = getCartItemEntity(cart, product);

        itemToUpdate.setQuantity(quantity);

        setCartUpdatedTime(cart);

        return cartItemMapper.entityToDto(
                cartItemRepository.save(itemToUpdate));
    }

    @Override
    @Transactional
    public void deleteItemFromCart(String id, Long productId) {
        cartValidator.validateUserIsCartOwner(id);

        Cart cart = getCartEntity(id);
        Product product = productService.getProductEntity(productId);

        cartValidator.validateProductInCart(cart, product);

        setCartUpdatedTime(cart);

        cartItemRepository.deleteByCartAndProduct(cart, product);
    }

    @Override
    @Transactional
    public void deleteCart(String id) {
        cartValidator.validateUserIsCartOwner(id);

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

    private void setCartUpdatedTime(Cart cart) {
        cart.setUpdatedAt(LocalDateTime.now());
    }

    private CartItem getCartItemEntity(Cart cart, Product product) {
        return cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("product with id=%s not found in cart", product.getId())));
    }
}
