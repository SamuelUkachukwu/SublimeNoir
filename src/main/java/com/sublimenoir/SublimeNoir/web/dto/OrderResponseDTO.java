package com.sublimenoir.SublimeNoir.web.dto;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
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
        this.items = new ArrayList<>();
        this.total = order.calculateTotal();
    }
}