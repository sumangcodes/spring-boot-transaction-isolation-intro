package com.example.isolation_demo.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.isolation_demo.entity.Order;
import com.example.isolation_demo.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<Order> readOrdersUncommitted() {
        return orderRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<Order> readOrdersRepeatableRead() {
        return orderRepository.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Order> readOrdersSerializable() {
        return orderRepository.findAll();
    }
}
