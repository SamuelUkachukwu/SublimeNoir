package com.sublimenoir.SublimeNoir.service;

import com.sublimenoir.SublimeNoir.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    // --- Basic CRUD
    Optional<Product> findById(Long id);
    Iterable<Product> findAll();
    Product save(Product product);
    Product update(Long id, Product updated);
    void deleteById(Long id);

    // --- Queries mirroring repository
    List<Product> findByBrand(String brand);
    List<Product> findByNameContaining(String keyword);
    List<Product> findByPriceBetween(double low, double high);
    List<Product> findBySizeML(int size);

    // --- Business Operations
    Product createProduct(String name, String brand, double price, int sizeML, int quantity);
}
