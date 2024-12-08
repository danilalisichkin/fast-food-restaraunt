package com.fastfoodrestaraunt.backend.controller.api;

import com.fastfoodrestaraunt.backend.core.dto.cart.CartDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CartItemDto> addCartItem(
            @PathVariable String id,
            @RequestBody CartItemAddingDto addingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<CartItemDto> updateCartItem(
            @PathVariable String id,
            @PathVariable Long itemId,
            @RequestBody Long quantity) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable String id,
            @PathVariable Long itemId) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clearCartById(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
