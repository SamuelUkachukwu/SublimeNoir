package com.sublimenoir.SublimeNoir.service;

import com.sublimenoir.SublimeNoir.domain.entity.User;

import java.util.Optional;

public interface UserService {
    // --- Basic CRUD
    User save(User user);
    Optional<User> findById(Long id);
    User update(Long id, User updated);
    void deleteById(Long id);
    Iterable<User> findAll();

    // --- Queries mirroring repository
    Optional<User> findByEmail(String email);
    User findByUsername(String username);

    // --- Business Operations
    User registerUser(String username, String email, String firstName, String lastName);

}

