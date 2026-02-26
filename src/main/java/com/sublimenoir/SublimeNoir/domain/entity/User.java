package com.sublimenoir.SublimeNoir.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --Setters
    @Setter
    @Column(unique = true)
    private String username;

    @Setter
    @Column(unique = true)
    private String email;

    @Setter
    private String firstName;

    @Setter
    private String lastName;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private final List<Order> orders = new ArrayList<>();

    public User() {
        super();
    }

    public User(String username, String email, String firstName, String lastName) {
        super();
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setUser(null);
    }
}