package com.sublimenoir.SublimeNoir.service.impl;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.domain.repository.ProductRepository;
import com.sublimenoir.SublimeNoir.exception.ProductNotFoundException;
import com.sublimenoir.SublimeNoir.service.interfaces.ProductService;
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

    // --- CRUD
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product save(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long id, Product updated) {

        validateProduct(updated);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        product.setName(updated.getName());
        product.setBrand(updated.getBrand());
        product.setPrice(updated.getPrice());
        product.setSizeML(updated.getSizeML());

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
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
        return productRepository.findByNameContainingIgnoreCase(keyword);
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


    // --- Business logic
    @Override
    @Transactional
    public Product createProduct(String name, String brand, double price, int sizeML, int quantity) {

        requireNotBlank(name, "Name");
        requireNotBlank(brand, "Brand");

        if (price <= 0)
            throw new IllegalArgumentException("Price must be positive");

        if (sizeML <= 0)
            throw new IllegalArgumentException("Size must be positive");

        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative");

        if (productRepository.existsByNameAndBrand(name, brand)) {
            throw new IllegalArgumentException("Product already exists");
        }

        Product product = new Product(name, brand, price, sizeML, quantity);

        return productRepository.save(product);
    }

    // --- Validation helpers
    private void validateProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product must not be null");

        requireNotBlank(product.getName(), "Name");

        if (product.getPrice() <= 0)
            throw new IllegalArgumentException("Price must be positive");
    }

    private void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException(field + " is required");
    }
}
