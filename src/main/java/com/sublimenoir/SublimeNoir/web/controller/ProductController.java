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
        throw new ProductNotFoundException("Product with id " + id + " not found");
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody ProductRequestDTO updated) {
        Product saved = productService.update(id, updated);
        return new ProductResponseDTO(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/brand/{brand}")
    public List<ProductResponseDTO> findByBrand(@PathVariable String brand) {

        List<Product> products = productService.findByBrand(brand);
        List<ProductResponseDTO> dtos = new ArrayList<>();

        for (Product product : products) {
            dtos.add(new ProductResponseDTO(product));
        }

        return dtos;
    }

    @GetMapping("/search")
    public List<ProductResponseDTO> findByNameContaining(
            @RequestParam String keyword) {

        List<Product> products = productService.findByNameContaining(keyword);
        List<ProductResponseDTO> dtos = new ArrayList<>();

        for (Product product : products) {
            dtos.add(new ProductResponseDTO(product));
        }

        return dtos;
    }

    @GetMapping("/price-range")
    public List<ProductResponseDTO> findByPriceBetween(
            @RequestParam double low,
            @RequestParam double high) {

        List<Product> products = productService.findByPriceBetween(low, high);
        List<ProductResponseDTO> dtos = new ArrayList<>();

        for (Product product : products) {
            dtos.add(new ProductResponseDTO(product));
        }

        return dtos;
    }

    @GetMapping("/size/{size}")
    public List<ProductResponseDTO> findBySizeML(@PathVariable int size) {

        List<Product> products = productService.findBySizeML(size);
        List<ProductResponseDTO> dtos = new ArrayList<>();

        for (Product product : products) {
            dtos.add(new ProductResponseDTO(product));
        }

        return dtos;
    }
}