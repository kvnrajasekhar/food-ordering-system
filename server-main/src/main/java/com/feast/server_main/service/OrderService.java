package com.feast.server_main.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feast.server_main.model.Order;
import com.feast.server_main.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        order.setDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserUserId(userId);
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }
}