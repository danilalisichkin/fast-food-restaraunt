package com.fastfoodrestaraunt.backend.controller.api;

import com.fastfoodrestaraunt.backend.controller.doc.CartControllerDoc;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemDto;
import com.fastfoodrestaraunt.backend.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController implements CartControllerDoc {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable String id) {
        CartDto cart = cartService.getCart(id);

        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CartItemDto> addCartItem(
            @PathVariable String id, @RequestBody @Valid CartItemAddingDto addingDto) {

        CartItemDto item = cartService.addItemToCart(id, addingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem(
            @PathVariable String id,
            @PathVariable Long productId,
            @RequestBody @NotNull @Positive Integer quantity) {

        CartItemDto item = cartService.updateItemInCart(id, productId, quantity);

        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/{id}/items/{productId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable String id, @PathVariable Long productId) {
        cartService.deleteItemFromCart(id, productId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clearCartById(@PathVariable String id) {
        cartService.deleteCart(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
