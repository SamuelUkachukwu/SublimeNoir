package com.sublimenoir.SublimeNoir.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>
{
    List<Product> findByBrand(String brand);

    List<Product> findByPriceBetween(double low, double high);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findBySizeML(int size);

    boolean existsByNameAndBrand(String name, String brand);
}
