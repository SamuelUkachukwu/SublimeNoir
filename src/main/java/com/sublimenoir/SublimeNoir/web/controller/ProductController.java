package com.sublimenoir.SublimeNoir.web.controller;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.exception.ProductNotFoundException;
import com.sublimenoir.SublimeNoir.service.interfaces.ProductService;
import com.sublimenoir.SublimeNoir.web.dto.ProductRequestDTO;
import com.sublimenoir.SublimeNoir.web.dto.ProductResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO dto) {

        Product saved = productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponseDTO(saved));
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        List<ProductResponseDTO> dtos = new ArrayList<>();
        Iterable<Product> products = productService.findAll();
        for (Product product : products) {
            dtos.add(new ProductResponseDTO(product));
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        Optional<Product> result = productService.findById(id);
        if (result.isPresent()) {
            ProductResponseDTO dto = new ProductResponseDTO(result.get());
            return ResponseEntity.ok(dto);
        }
        throw new ProductNotFoundException("Car with id " + id + " not found");
    }

}