package com.project.controllers;

import com.project.dtos.AddToCartRequest;
import com.project.dtos.UpdateCartItemRequest;
import com.project.dtos.CheckoutResponse;
import com.project.entities.Cart;
import com.project.services.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) { this.cartService = cartService; }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartRequest req) {
        return ResponseEntity.ok(cartService.addItem(req));
    }

    @PutMapping("/item")
    public ResponseEntity<Cart> updateCartItem(@RequestBody UpdateCartItemRequest req) {
        return ResponseEntity.ok(cartService.updateItem(req));
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeItem(cartItemId));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<CheckoutResponse> checkout(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.checkout(userId));
    }
}
