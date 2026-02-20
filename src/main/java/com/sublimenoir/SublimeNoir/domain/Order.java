package com.sublimenoir.SublimeNoir.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
        super();
        this.orderDate = LocalDate.now();
    }

    public Order(User user) {
        this.user = user;
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PENDING;
    }

    // --- Getters
    public Long getOrderId() {
        return orderId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getOrderDate() { return orderDate; }

    public OrderStatus getStatus() { return status; }

    public List<OrderItem> getItems() { return items; }

    // --- Setters
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addItem(Product product, int quantity) {
        OrderItem item = new OrderItem(
                this,
                product,
                quantity,
                product.getPrice()
        );
        items.add(item);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
}