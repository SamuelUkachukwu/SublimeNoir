package com.sublimenoir.SublimeNoir.service.impl;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.domain.repository.ProductRepository;
import com.sublimenoir.SublimeNoir.exception.ProductNotFoundException;
import com.sublimenoir.SublimeNoir.service.interfaces.ProductService;
import com.sublimenoir.SublimeNoir.web.dto.ProductRequestDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository repository) {
        this.productRepository = repository;
    }


    // ---Basic CRUD
    @Override
    @Transactional
    public Product save(ProductRequestDTO dto) {
        Product product = new Product();
        updateProductFields(product, dto);
        validateProduct(product);

        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product update(Long id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + id + " not fround"));

        updateProductFields(existing, dto);

        validateProduct(existing);

        return productRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    // --- Product field updater
    private void updateProductFields(@NonNull Product product, ProductRequestDTO dto) {
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setSizeML(dto.getSizeML());
        product.setQuantity(dto.getQuantity());
    }

    // --- Queries
    @Override
    public List<Product> findByBrand(String brand) {
        requireNotBlank(brand, "Brand");
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> findByNameContaining(String keyword) {
        requireNotBlank(keyword, "Keyword");
        return productRepository.findByNameContaining(keyword);
    }

    @Override
    public List<Product> findByPriceBetween(double low, double high) {
        if (low > high)
            throw new IllegalArgumentException("Low must be less than or equal to high");
        return productRepository.findByPriceBetween(low, high);
    }

    @Override
    public List<Product> findBySizeML(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size must be positive");
        return productRepository.findBySizeML(size);
    }

    // --- Validation helpers
    private void validateProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product must not be null");

        requireNotBlank(product.getName(), "Name");
        requireNotBlank(product.getBrand(), "Brand");

        if (product.getPrice() <= 0)
            throw new IllegalArgumentException("Price must be positive");

        if (product.getSizeML() <= 0)
            throw new IllegalArgumentException("Size must be positive");

        if (product.getQuantity() < 0)
            throw new IllegalArgumentException("Quantity cannot be negative");
    }

    private void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException(field + " is required");
    }
}
