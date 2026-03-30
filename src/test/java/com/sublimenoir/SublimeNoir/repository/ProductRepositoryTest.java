package com.sublimenoir.SublimeNoir.repository;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveProduct() {
        productRepository.save(new Product("Shamrock Dew", "SublimeMagic", 100.0, 50, 10));
        List<Product> results = productRepository.findByNameContaining("Shamrock");
        assertThat(results).isNotEmpty();
    }

    @Test
    void testFindByBrand() {
        productRepository.save(new Product("Test1", "SublimeMagic", 100, 50, 10));
        List<Product> results = productRepository.findByBrand("SublimeMagic");
        assertFalse(results.isEmpty());
    }

    @Test
    void testFindByNameContaining() {
        productRepository.save(new Product("Shamrock Dew", "SublimeMagic", 100, 50, 10));
        List<Product> products = productRepository.findByNameContaining("Dew");
        assertEquals(1, products.size());
    }

    @Test
    void testFindByPriceBetween() {
        productRepository.save(new Product("Dusk to Dawn", "SublimeNoir", 50, 50, 5));
        productRepository.save(new Product("EliteMoose", "SublimeNoir", 200, 50, 5));

        List<Product> results = productRepository.findByPriceBetween(40, 100);

        assertEquals(1, results.size());
    }

    @Test
    void testUpdateProduct() {
        Product product = productRepository.save(new Product("009 Bond", "SublimeMagic", 100, 50, 5));
        product.setName("007 Bond");
        productRepository.save(product);

        Product updated = productRepository.findById(product.getProductId()).get();
        assertEquals("007 Bond", updated.getName());
    }

    @Test
    void testDeleteProduct() {
        Product product = productRepository.save(
                new Product("EldenRing", "SublimeMagic", 100, 50, 5)
        );

        productRepository.delete(product);
        Optional<Product> deleted = productRepository.findById(product.getProductId());
        assertTrue(deleted.isEmpty());
    }
}
