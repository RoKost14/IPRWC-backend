package com.example.iprwcserver.repository;

import com.example.iprwcserver.model.Cart;
import com.example.iprwcserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
   Optional<Cart> findCartByUserId(UUID userId);

}
