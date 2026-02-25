package com.sublimenoir.SublimeNoir.service.impl;

import com.sublimenoir.SublimeNoir.domain.entity.User;
import com.sublimenoir.SublimeNoir.domain.repository.UserRepository;
import com.sublimenoir.SublimeNoir.exception.UserNotFoundException;
import com.sublimenoir.SublimeNoir.service.interfaces.UserService;
import com.sublimenoir.SublimeNoir.web.dto.UserRequestDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    // --- CRUD
    @Override
    @Transactional
    public User save(UserRequestDTO dto) {
        validateUser(dto);

        User user = new User();
        updateUserFields(user, dto);

        return userRepository.save(user);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User update(Long id, UserRequestDTO updated) {
        validateUser(updated);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        // update allowed fields
        updateUserFields(user, updated);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    // --- Queries
    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // --- Business logic
    @Override
    @Transactional
    public User registerUser(String username, String email, String firstName, String lastName) {

        requireNotBlank(username, "Username");
        requireNotBlank(email, "Email");

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User(username, email, firstName, lastName);

        return userRepository.save(user);
    }

    // --- Helper function
    private void updateUserFields(@NonNull User user, UserRequestDTO dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
    }

    // --- validation helper
    private void validateUser(UserRequestDTO user) {
        if (user == null) throw new IllegalArgumentException("User must not be null");

        requireNotBlank(user.getUsername(), "Username");
        requireNotBlank(user.getEmail(), "Email");
        requireNotBlank(user.getFirstName(), "First Name");
        requireNotBlank(user.getLastName(), "Last Name");
    }

    private void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }
}
