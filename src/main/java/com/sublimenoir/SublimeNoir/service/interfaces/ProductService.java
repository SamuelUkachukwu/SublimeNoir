package com.sublimenoir.SublimeNoir.service.interfaces;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.web.dto.ProductRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    // --- Basic CRUD
    Product save(ProductRequestDTO dto);

    Iterable<Product> findAll();

    Optional<Product> findById(Long id);

    Product update(Long id, ProductRequestDTO dto);

    void deleteById(Long id);

    // --- Queries
    List<Product> findByBrand(String brand);

    List<Product> findByNameContaining(String keyword);

    List<Product> findByPriceBetween(double low, double high);

    List<Product> findBySizeML(int size);
}
