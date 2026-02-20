package com.sublimenoir.SublimeNoir.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private String brand;
    private double price;
    private int sizeML;
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

    // --- Getters
    public Long getProductId() { return productId; }

    public String getName() { return name; }

    public String getBrand() { return brand; }

    public double getPrice() { return price; }

    public int getSizeML() { return sizeML; }

    public int getQuantity() { return quantity; }

    // --- Setters
    public void setName(String name) { this.name = name; }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSizeML(int sizeML) {
        this.sizeML = sizeML;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

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
