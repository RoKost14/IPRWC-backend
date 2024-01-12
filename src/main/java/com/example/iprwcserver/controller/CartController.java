package com.example.iprwcserver.controller;

import com.example.iprwcserver.dao.CartDAO;
import com.example.iprwcserver.model.Cart;
import com.example.iprwcserver.model.CartItem;
import jakarta.persistence.SqlResultSetMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartDAO cartDAO;

    @PostMapping(value = "/order")
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            System.out.println(item.toString());
            if (item.getSizeId() == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "SizeId cannot be null for CartItem: " + item.toString()));
            }
            if (!cartDAO.isProductInStock(item)) {
                return ResponseEntity.badRequest().body(Map.of("message", "Product with ID: " + item.getSizeId() + " is out of stock."));
            }
            cartDAO.updateStock(item);
        }
        return new ResponseEntity<>(Map.of("message", "Gaat helemaal goed"), HttpStatus.OK);
    }

    @PostMapping(value = "/save/{userId}")
    public ResponseEntity<Cart> saveCart(@PathVariable UUID userId, @RequestBody Cart cart) {
        Cart savedCart = cartDAO.saveCart(userId, cart);
        if (savedCart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savedCart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable UUID userId) {
        Cart cart = cartDAO.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }
}
