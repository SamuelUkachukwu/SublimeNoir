package com.sublimenoir.SublimeNoir.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    // --- Getters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    // --- Setters
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String brand;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private int sizeML;
    @Getter
    @Setter
    private int quantity;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Product() {
        super();
    }

    public Product(String name, String brand, double price, int sizeML, int quantity) {
        super();
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.sizeML = sizeML;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", sizeML=" + sizeML +
                '}';
    }
}
