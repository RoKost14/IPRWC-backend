package com.example.iprwcserver.repository;

import com.example.iprwcserver.model.Cart;
import com.example.iprwcserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
   @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
   Cart findCartByUserId(@Param("userId") UUID userId);

}
