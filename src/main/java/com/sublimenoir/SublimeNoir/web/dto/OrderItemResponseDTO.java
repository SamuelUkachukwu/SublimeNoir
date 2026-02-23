package com.sublimenoir.SublimeNoir.web.dto;

import com.sublimenoir.SublimeNoir.domain.entity.OrderItem;

public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double priceAtPurchase;

    public OrderItemResponseDTO(OrderItem item) {
        this.productId = item.getProduct().getProductId();
        this.productName = item.getProduct().getName();
        this.quantity = item.getQuantity();
        this.priceAtPurchase = item.getPriceAtPurchase();
    }

    // getters
}
