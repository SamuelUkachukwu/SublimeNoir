package com.sublimenoir.SublimeNoir.service;

import com.sublimenoir.SublimeNoir.domain.entity.User;
import com.sublimenoir.SublimeNoir.domain.repository.UserRepository;
import com.sublimenoir.SublimeNoir.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    // --- CRUD
    @Override
    @Transactional
    public User save(User user) {
        validateUser(user);
        return userRepository.save(user);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User update(Long id, User updated) {
        validateUser(updated);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        // update allowed fields
        user.setUsername(updated.getUsername());
        user.setEmail(updated.getEmail());
        user.setFirstName(updated.getFirstName());
        user.setLastName(updated.getLastName());

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

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User(username, email, firstName, lastName);

        return userRepository.save(user);
    }

    // --- validation helper
    private void validateUser(User user) {
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
