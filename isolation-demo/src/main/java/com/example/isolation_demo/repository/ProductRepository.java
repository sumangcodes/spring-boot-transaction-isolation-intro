package com.example.isolation_demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.isolation_demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
