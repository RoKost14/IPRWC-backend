package com.example.iprwcserver.dao;

import com.example.iprwcserver.model.Product;
import com.example.iprwcserver.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductDAO extends Product {
    private final ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.save(product);
    }
    public Product deleteById(UUID id){
        Product product = productRepository.findById(id).orElse(null);
        productRepository.deleteById(id);
        return product;
    }
   public Product updateProductById(UUID id, Product product){
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImage(product.getImage());
        productRepository.save(existingProduct);
        return existingProduct;
    }
    public Product findById(UUID id){
        return productRepository.findById(id).orElse(null);
    }
    public List<Product> findAll(){
        return productRepository.findAll();
    }
}
