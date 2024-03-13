package com.example.iprwcserver.repository;

import com.example.iprwcserver.model.Cart;
import com.example.iprwcserver.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> deleteCartItemsByCartId(UUID cartId);
    Optional<CartItem> findCartItemByCartId(UUID cartId);

    Optional<CartItem> deleteAllByCartId(UUID cartId);
}
