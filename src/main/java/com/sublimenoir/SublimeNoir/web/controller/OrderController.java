package com.sublimenoir.SublimeNoir.web.controller;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;
import com.sublimenoir.SublimeNoir.exception.OrderNotFoundException;
import com.sublimenoir.SublimeNoir.service.interfaces.OrderService;
import com.sublimenoir.SublimeNoir.web.dto.OrderResponseDTO;
import com.sublimenoir.SublimeNoir.web.dto.OrderRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO dto) {

        Order order = orderService.createOrder(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OrderResponseDTO(order));
    }

    @GetMapping
    public List<OrderResponseDTO> getAll() {

        List<OrderResponseDTO> dtos = new ArrayList<>();

        for (Order order : orderService.findAll()) {
            dtos.add(new OrderResponseDTO(order));
        }

        return dtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {

        Optional<Order> result = orderService.findById(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(new OrderResponseDTO(result.get()));
        }

        throw new OrderNotFoundException("Order not found");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.deleteById(id);
    }

    @PostMapping("/{orderId}/products/{productId}")
    public OrderResponseDTO addProduct(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @RequestParam int quantity) {

        Order order = orderService.addProduct(orderId, productId, quantity);
        return new OrderResponseDTO(order);
    }

    @DeleteMapping("/{orderId}/products/{productId}")
    public OrderResponseDTO removeProduct(
            @PathVariable Long orderId,
            @PathVariable Long productId) {

        Order order = orderService.removeProduct(orderId, productId);

        return new OrderResponseDTO(order);
    }

    @PutMapping("/{orderId}/status")
    public OrderResponseDTO changeStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        Order order = orderService.changeStatus(orderId, status);
        return new OrderResponseDTO(order);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponseDTO> findByUser(@PathVariable Long userId) {

        List<OrderResponseDTO> dtos = new ArrayList<>();

        for (Order order : orderService.findByUser(userId)) {
            dtos.add(new OrderResponseDTO(order));
        }

        return dtos;
    }

    @GetMapping("/status/{status}")
    public List<OrderResponseDTO> findByStatus(@PathVariable OrderStatus status) {

        List<OrderResponseDTO> dtos = new ArrayList<>();

        for (Order order : orderService.findByStatus(status)) {
            dtos.add(new OrderResponseDTO(order));
        }

        return dtos;
    }

    @PutMapping("/{orderId}/products/{productId}")
    public OrderResponseDTO updateProductQuantity(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @RequestParam int quantity) {

        Order order = orderService.updateProductQuantity(orderId, productId, quantity);

        return new OrderResponseDTO(order);
    }
}