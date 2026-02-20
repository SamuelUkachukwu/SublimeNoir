package com.sublimenoir.SublimeNoir.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    // --- Getters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --Setters
    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    @Column(unique = true)
    private String email;
    @Setter
    @Getter
    private String firstName;
    @Setter
    @Getter
    private String lastName;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Order> orders = new ArrayList<>();

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

}