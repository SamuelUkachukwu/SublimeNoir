package com.sublimenoir.SublimeNoir.web.dto;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String brand;
    private double price;
    private int sizeML;
    private int quantity;

    // ---Constructor
    public ProductResponseDTO(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.sizeML = product.getSizeML();
        this.quantity = product.getQuantity();
    }

    // ---Getters
}
