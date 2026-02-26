package com.sublimenoir.SublimeNoir.service.interfaces;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;
import com.sublimenoir.SublimeNoir.web.dto.OrderRequestDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    // --- CRUD
    Optional<Order> findById(Long id);
    Iterable<Order> findAll();
    Order createOrder(OrderRequestDTO dto);
    void deleteById(Long id);

    // --- Queries
    List<Order> findByUser(Long userId);
    List<Order> findByStatus(OrderStatus status);

    // --- Business rules
    Order addProduct(Long orderId, Long productId, int quantity);

    Order removeProduct(Long orderId, Long productId);

    Order changeStatus(Long orderId, OrderStatus status);

    Order updateProductQuantity(Long orderId, Long productId, int quantity);

    double calculateTotal(Long orderId);
}
