package com.sublimenoir.SublimeNoir.service.impl;

import com.sublimenoir.SublimeNoir.domain.entity.*;
import com.sublimenoir.SublimeNoir.domain.repository.OrderRepository;
import com.sublimenoir.SublimeNoir.domain.repository.ProductRepository;
import com.sublimenoir.SublimeNoir.domain.repository.UserRepository;
import com.sublimenoir.SublimeNoir.service.interfaces.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private  final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // --- CRUD
    @Override
    @Transactional
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = new Order(user);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    // --- Queries
    @Override
    @Transactional
    public List<Order> findByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return orderRepository.findByUser(user);
    }

    @Override
    @Transactional
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    // --- Business rules
    @Override
    @Transactional
    public Order addProduct(Long orderId, Long productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock to complete this order");
        }

        // --- Reduce product stock
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        order.addItem(product, quantity);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order removeProduct(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Optional<OrderItem> itemOpt = order.getItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst();

        if (itemOpt.isPresent()) {
            OrderItem item = itemOpt.get();
            // Restore product stock
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);

            order.removeItem(item);
            return orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product not in order");
        }
    }

    @Override
    @Transactional
    public Order changeStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public double calculateTotal(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        double totalPrice = 0;
        for (OrderItem item : order.getItems()) {
            totalPrice += item.getPriceAtPurchase() * item.getQuantity();
        }
        return totalPrice;
    }
}
