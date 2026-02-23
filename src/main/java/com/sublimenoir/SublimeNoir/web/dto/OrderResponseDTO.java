package com.sublimenoir.SublimeNoir.web.dto;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseDTO {
    private Long orderId;
    private Long userId;
    private LocalDate orderDate;
    private OrderStatus status;
    private List<OrderItemResponseDTO> items;
    private double total;

    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();

        this.items = order.getItems()
                .stream()
                .map(OrderItemResponseDTO::new)
                .collect(Collectors.toList());

        this.total = order.getItems()
                .stream()
                .mapToDouble(i -> i.getPriceAtPurchase() * i.getQuantity())
                .sum();
    }

    // getters
}
