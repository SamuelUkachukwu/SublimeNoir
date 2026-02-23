package com.sublimenoir.SublimeNoir.web.dto;

import com.sublimenoir.SublimeNoir.domain.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double priceAtPurchase;

    public OrderItemResponseDTO(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getProductId();
        this.productName = orderItem.getProduct().getName();
        this.quantity = orderItem.getQuantity();
        this.priceAtPurchase = orderItem.getPriceAtPurchase();
    }

    // getters and setters? lombok!
}