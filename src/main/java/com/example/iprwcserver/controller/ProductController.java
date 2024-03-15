package com.example.iprwcserver.controller;

import com.example.iprwcserver.dao.ProductDAO;
import com.example.iprwcserver.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDAO productDAO;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<UUID> createProduct(@RequestBody Product product) {
        Product createdProduct = productDAO.create(product);
        return new ResponseEntity<>(createdProduct.getId(), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") UUID id, @RequestBody Product product) {
        Product updatedProduct = productDAO.updateProductById(id, product);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);

    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") UUID id) {
        Product deletedProduct = productDAO.deleteById(id);
        if (deletedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedProduct);
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        Iterable<Product> products = productDAO.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping(value = "/details/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") UUID id) {
        Product product = productDAO.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
