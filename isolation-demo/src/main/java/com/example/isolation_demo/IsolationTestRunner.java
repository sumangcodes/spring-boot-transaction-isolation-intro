package com.example.isolation_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.isolation_demo.entity.Product;
import com.example.isolation_demo.repository.ProductRepository;
import com.example.isolation_demo.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class IsolationTestRunner implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager; // Injecting EntityManager
    private final ProductRepository productRepository;
    private final ProductService productService;

    public IsolationTestRunner(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Step 1: Create a product
        Product product = new Product();
        product.setName("Laptop");
        product.setQuantity(10);
        productRepository.save(product);

        // Ensure transaction is flushed (committed) before starting threads
        entityManager.flush(); // Force the product to be saved to DB

        // Add a delay to ensure the product is fully committed
        Thread.sleep(1000);

        System.out.println("----------------------------------------------------------------------------------");
        // Fetch and verify product creation
        Product createdProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product creation failed!"));

        System.out.println("Product created: " + createdProduct.getId());
        System.out.println("=================================================================================");
        // Step 2: Simulate concurrent transactions
        Thread updateThread = new Thread(() -> {
            try {
                productService.updateProduct(product.getId(), 20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread readThread = new Thread(() -> {
            try {
                productService.readProduct(product.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

       updateThread.start();
       readThread.start();

        updateThread.join(); // Ensure update thread completes
        readThread.join(); // Ensure read thread completes

        Thread.sleep(2000); // Give time for threads to execute
    }
}
