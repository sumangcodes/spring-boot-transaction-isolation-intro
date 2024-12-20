package com.example.isolation_demo.service;


import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import com.example.isolation_demo.entity.Product;
import com.example.isolation_demo.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void updateProduct(Long productId, int newQuantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            System.out.println("Updating Product - Current Quantity: " + product.getQuantity());
            product.setQuantity(newQuantity);
            productRepository.save(product);
            System.out.println("Updated Product Quantity to: " + newQuantity);
        } else {
            System.out.println("Product with ID " + productId + " not found during update.");
        }
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void readProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            System.out.println("Read Product - Quantity: " + product.getQuantity());
        } else {
            System.out.println("Product with ID " + productId + " not found during read.");
        }
    }

}
