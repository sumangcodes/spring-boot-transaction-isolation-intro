package com.example.isolation_demo.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.example.isolation_demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
