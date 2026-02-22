package com.sublimenoir.SublimeNoir.service;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    // --- CRUD
    Optional<Order> findById(Long id);
    Iterable<Order> findAll();
    Order createOrder(Long userId);
    void deleteById(Long id);

    // --- Queries
    List<Order> findByUser(Long userId);
    List<Order> findByStatus(OrderStatus status);

    // --- Business rules
    Order addProduct(Long orderId, Long productId, int quantity);
    Order removeProduct(Long orderId, Long productId);

    Order changeStatus(Long orderId, OrderStatus status);

    double calculateTotal(Long orderId);
}
