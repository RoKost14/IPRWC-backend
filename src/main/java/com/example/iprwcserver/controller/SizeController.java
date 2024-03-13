package com.example.iprwcserver.controller;

import com.example.iprwcserver.dao.SizeDAO;
import com.example.iprwcserver.model.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final SizeDAO sizeDAO;

    //    @PostMapping("/product/create/{productId}")
//    public ResponseEntity<Size> createSizeOnProduct(@PathVariable UUID productId, @RequestBody Size size) {
//        Size newSize = sizeDAO.createSizeOnProduct(productId, size.getSize(), size.getStock());
//        if (newSize == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(newSize);
//    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/product/create/{productId}")
    public ResponseEntity<List<Size>> createSizesOnProduct(@PathVariable UUID productId, @RequestBody List<Size> sizes) {
        List<Size> newSizes = sizeDAO.createSizesOnProduct(productId, sizes);
        if (newSizes == null || newSizes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newSizes);
    }

    // In SizeController.java
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<List<Size>> updateSizesOnProduct(@PathVariable UUID productId, @RequestBody List<Size> sizes) {
        List<Size> updatedSizes = sizeDAO.updateSizesOnProduct(productId, sizes);
        if (updatedSizes == null || updatedSizes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSizes);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Size>> getSizesByProductId(@PathVariable UUID productId) {
        List<Size> sizes = sizeDAO.getSizesByProductId(productId);
        if (sizes == null || sizes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sizes);
    }

}
