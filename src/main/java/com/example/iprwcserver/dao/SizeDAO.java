package com.example.iprwcserver.dao;

import com.example.iprwcserver.model.Product;
import com.example.iprwcserver.model.Size;
import com.example.iprwcserver.repository.ProductRepository;
import com.example.iprwcserver.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SizeDAO {
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;

    public Size createSizeOnProduct(UUID productId, int size, int stock) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        Size newSize = new Size();
        newSize.setSize(size);
        newSize.setProduct(product);
        product.getSizes().add(newSize);
        productRepository.save(product);
        return newSize;
    }

    // In SizeDAO.java
    public List<Size> updateSizesOnProduct(UUID productId, List<Size> sizes) {
        // Retrieve the product
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }

        // Update each size and save them
        for (Size size : sizes) {
            Size existingSize = sizeRepository.findBySizeAndProductId(size.getSize(), productId);
            if (existingSize == null) {
                return null;
            }
            existingSize.setSize(size.getSize());
            sizeRepository.save(existingSize);
        }

        return sizes;
    }

    public List<Size> createSizesOnProduct(UUID productId, List<Size> sizes) {
        // Retrieve the product
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }

        // Set the product for each size and save them
        for (Size size : sizes) {
            size.setProduct(product);
            sizeRepository.save(size);
        }

        return sizes;
    }
    
    public Size getSizesFromProduct(int size, UUID productId){
        return sizeRepository.findBySizeAndProductId(size, productId);
    }


    public List<Size> getSizesByProductId(UUID productId) {
        return sizeRepository.findByProductId(productId);
    }
    public Size findById(UUID id) {
        return sizeRepository.findById(id).orElse(null);
    }

    public Size save(Size size) {
        return sizeRepository.save(size);
    }
}
