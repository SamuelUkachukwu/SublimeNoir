package com.sublimenoir.SublimeNoir.web.controller;

import com.sublimenoir.SublimeNoir.domain.entity.User;
import com.sublimenoir.SublimeNoir.exception.UserNotFoundException;
import com.sublimenoir.SublimeNoir.service.interfaces.UserService;
import com.sublimenoir.SublimeNoir.web.dto.UserResponseDTO;
import com.sublimenoir.SublimeNoir.web.dto.UserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {

        User saved = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(saved));
    }

    @GetMapping
    public List<UserResponseDTO> getAll() {
        List<UserResponseDTO> dtos = new ArrayList<>();
        Iterable<User> users = userService.findAll();
        for (User user : users) {
            dtos.add(new UserResponseDTO(user));
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        User user = userService.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + id + " not found"));

        return new UserResponseDTO(user);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable Long id,
            @RequestBody UserRequestDTO dto) {

        User updated = userService.update(id, dto);
        return new UserResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/get-email")
    public UserResponseDTO getByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User with email: " + email + " not found"));

        return new UserResponseDTO(user);
    }

    @GetMapping("/get-username")
    public UserResponseDTO getByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        return new UserResponseDTO(user);
    }
}
