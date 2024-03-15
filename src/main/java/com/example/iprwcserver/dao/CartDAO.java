package com.example.iprwcserver.dao;

import com.example.iprwcserver.model.Cart;
import com.example.iprwcserver.model.CartItem;
import com.example.iprwcserver.model.Size;
import com.example.iprwcserver.model.User;
import com.example.iprwcserver.repository.CartItemRepository;
import com.example.iprwcserver.repository.CartRepository;
import com.example.iprwcserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartDAO {
    private final SizeDAO sizeDAO;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public Cart getCartByUserId(UUID userId) {
        return cartRepository.findCartByUserId(userId).orElse(null);

    }

    public void addItemToCart(UUID userId, CartItem cartItem) {
        Cart cart = cartRepository.findCartByUserId(userId).orElse(null);
        if (cart == null) {
            throw new IllegalArgumentException("No Cart found with id: " + userId);
        }
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
    }
    public void clearCart(UUID userId) {
    Cart cart = cartRepository.findCartByUserId(userId).orElse(null);
    if (cart == null) {
        throw new IllegalArgumentException("No Cart found with id: " + userId);
    }
        System.out.println(cart.getCartItems().size());
        cart.getCartItems().clear();
        cartRepository.save(cart);
}
}
