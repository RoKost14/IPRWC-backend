package com.example.iprwcserver.repository;

import com.example.iprwcserver.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SizeRepository extends JpaRepository<Size, UUID> {
    List<Size> findByProductId(UUID productId);
    Size findBySizeAndProductId(int size, UUID productId);
}
