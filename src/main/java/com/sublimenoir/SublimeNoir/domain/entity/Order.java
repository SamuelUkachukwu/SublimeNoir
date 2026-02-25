package com.sublimenoir.SublimeNoir.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDate orderDate;

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private final List<OrderItem> items = new ArrayList<>();

    public Order() {
        super();
        this.orderDate = LocalDate.now();
    }

    public Order(User user) {
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PENDING;
        this.user = user;
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

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPriceAtPurchase() * item.getQuantity();
        }
        return total;
    }
}