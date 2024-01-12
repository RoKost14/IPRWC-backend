package com.example.iprwcserver.dao;

import com.example.iprwcserver.model.Cart;
import com.example.iprwcserver.model.CartItem;
import com.example.iprwcserver.model.Size;
import com.example.iprwcserver.model.User;
import com.example.iprwcserver.repository.CartRepository;
import com.example.iprwcserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartDAO {
    private final SizeDAO sizeDAO;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public boolean isProductInStock(CartItem cartItem) {
        Size size = sizeDAO.findById(cartItem.getSizeId());
        if (size == null) {
            throw new IllegalArgumentException("No Size found with id: " + cartItem.getSizeId());
        }
        return size.getStock() != 0 && size.getStock() >= cartItem.getQuantity();
    }
    public void updateStock(CartItem cartItem) {
        Size size = sizeDAO.findById(cartItem.getSizeId());
        if (size == null) {
            throw new IllegalArgumentException("No Size found with id: " + cartItem.getSizeId());
        }
        size.setStock(size.getStock() - cartItem.getQuantity());
        sizeDAO.save(size);
    }
    public Cart saveCart(UUID userId, Cart cart){
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("No User found with id: " + userId);
        }
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    public Cart getCartByUserId(UUID userId){
        return cartRepository.findCartByUserId(userId);

    }

}
