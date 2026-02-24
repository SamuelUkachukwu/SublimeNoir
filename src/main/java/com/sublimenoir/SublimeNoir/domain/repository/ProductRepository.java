package com.sublimenoir.SublimeNoir.domain.repository;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>
{
    List<Product> findByBrand(String brand);

    List<Product> findByNameContaining(String keyword);

    List<Product> findByPriceBetween(double low, double high);

    List<Product> findBySizeML(int size);
}
