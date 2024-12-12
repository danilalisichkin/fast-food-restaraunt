package com.fastfoodrestaraunt.backend.controller.doc;

import com.fastfoodrestaraunt.backend.core.dto.cart.CartDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.cart.CartItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "token")
@Tag(name = "Cart API Controller", description = "Provides CRUD-operations with shopping carts and items in them")
public interface CartControllerDoc {

    @Operation(
            summary = "Get cart",
            description = "Allows to get user's shopping cart")
    ResponseEntity<CartDto> getCartById(
            @Parameter(
                    description = "Identifier of cart, similar to user's phone")
            @PathVariable String id);

    @Operation(
            summary = "Add cart item",
            description = "Allows to add product to shopping cart")
    ResponseEntity<CartItemDto> addCartItem(
            @Parameter(
                    description = "Identifier of cart, similar to user's phone")
            @PathVariable String id,
            @Parameter(
                    name = "Required data",
                    description = "Data that will be used to add product to cart", required = true)
            @RequestBody @Valid CartItemAddingDto addingDto);

    @Operation(
            summary = "Update cart item",
            description = "Allows to update item in shopping cart")
    ResponseEntity<CartItemDto> updateCartItem(
            @Parameter(
                    description = "Identifier of cart, similar to user's phone")
            @PathVariable String id,
            @Parameter(
                    description = "Identifier of product, which is added to cart")
            @PathVariable Long productId,
            @Parameter(
                    name = "Quantity of products",
                    description = "New quantity of product in cart that will be set", required = true)
            @RequestBody @NotNull @Positive Integer quantity);

    @Operation(
            summary = "Remove cart item",
            description = "Allows to remove product from shopping cart")
    ResponseEntity<Void> removeCartItem(
            @Parameter(
                    description = "Identifier of cart, similar to user's phone")
            @PathVariable String id,
            @Parameter(
                    description = "Identifier of product, which is added to cart")
            @PathVariable Long productId);

    @Operation(
            summary = "Clear/delete cart",
            description = "Allows to clear/delete user's shopping cart")
    ResponseEntity<Void> clearCartById(
            @Parameter(
                    description = "Identifier of cart, similar to user's phone")
            @PathVariable String id);
}
