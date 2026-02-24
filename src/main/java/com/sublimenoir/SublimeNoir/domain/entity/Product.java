package com.sublimenoir.SublimeNoir.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Setter
    private String name;
    @Setter
    private String brand;
    @Setter
    private double price;
    @Setter
    private int sizeML;
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

    // --- Getters Setters ? by lombok

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
