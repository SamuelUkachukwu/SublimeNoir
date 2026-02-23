package com.sublimenoir.SublimeNoir.web.dto;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import lombok.Data;

@Data
public class CreateProductRequestDTO {
    private String name;
    private String brand;
    private double price;
    private int sizeML;
    private int quantity;

    public CreateProductRequestDTO(Product product) {
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.sizeML = product.getSizeML();
        this.quantity = product.getQuantity();
    }
    // getters & setters? by lombok
}
