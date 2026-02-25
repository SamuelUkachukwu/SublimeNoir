package com.sublimenoir.SublimeNoir.service.interfaces;

import com.sublimenoir.SublimeNoir.domain.entity.User;
import com.sublimenoir.SublimeNoir.web.dto.UserRequestDTO;

import java.util.Optional;

public interface UserService {
    // --- Basic CRUD
    User save(UserRequestDTO dto);

    Optional<User> findById(Long id);

    Iterable<User> findAll();

    User update(Long id, UserRequestDTO updated);

    void deleteById(Long id);


    // --- Queries mirroring repository
    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    // --- Business Operations
    User registerUser(String username, String email, String firstName, String lastName);
}

