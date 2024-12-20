package com.example.isolation_demo.controller;


import org.springframework.web.bind.annotation.*;

import com.example.isolation_demo.entity.Order;
import com.example.isolation_demo.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return "Order created!";
    }

    @GetMapping("/read-uncommitted")
    public List<Order> readUncommitted() {
        return orderService.readOrdersUncommitted();
    }

    @GetMapping("/read-repeatable")
    public List<Order> readRepeatableRead() {
        return orderService.readOrdersRepeatableRead();
    }

    @GetMapping("/read-serializable")
    public List<Order> readSerializable() {
        return orderService.readOrdersSerializable();
    }
}
