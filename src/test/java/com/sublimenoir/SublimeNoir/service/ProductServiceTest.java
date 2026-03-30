package com.sublimenoir.SublimeNoir.service;

import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.exception.ProductNotFoundException;
import com.sublimenoir.SublimeNoir.service.impl.ProductServiceImpl;
import com.sublimenoir.SublimeNoir.web.dto.ProductRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "spring.profiles.active=test")
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Check that product are created ok")
    void testCreateProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Dark Horse");
        dto.setBrand("SublimeMagic");
        dto.setPrice(110);
        dto.setSizeML(60);
        dto.setQuantity(20);

        Product product = productService.save(dto);

        assertNotNull(product.getProductId());
        assertEquals("Dark Horse", product.getName());
    }

    @Test
    @DisplayName("Test should find product by it's id")
    void testFindProductById() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Mango");
        dto.setBrand("SublimeNoir");
        dto.setPrice(50);
        dto.setSizeML(30);
        dto.setQuantity(5);

        Product product = productService.save(dto);
        Optional<Product> found = productService.findById(product.getProductId());
        assertTrue(found.isPresent());
    }

    @Test
    @DisplayName("Testing products being updated")
    void testUpdateProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Melanine Blue");
        dto.setBrand("SublimeMagic");
        dto.setPrice(120);
        dto.setSizeML(80);
        dto.setQuantity(90);

        Product product = productService.save(dto);

        ProductRequestDTO updateDto = new ProductRequestDTO();
        updateDto.setName("Melanine Blue");
        updateDto.setBrand("SublimeMagic");
        updateDto.setPrice(120);
        updateDto.setSizeML(80);
        updateDto.setQuantity(300);

        Product updated = productService.update(product.getProductId(), updateDto);
        assertEquals(300, updated.getQuantity());
    }

    @Test
    @DisplayName("Test should delete a Product")
    void testDeleteProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Velvet Ada");
        dto.setBrand("SublimeNoir");
        dto.setPrice(100);
        dto.setSizeML(90);
        dto.setQuantity(15);

        Product product = productService.save(dto);
        productService.deleteById(product.getProductId());
        Optional<Product> deleted = productService.findById(product.getProductId());

        assertTrue(deleted.isEmpty());
    }

    @Test
    @DisplayName("Throw exception for invalid price")
    void testForInvalidPrice() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Velvet Ada");
        dto.setBrand("SublimeMagic");
        dto.setPrice(0);
        dto.setSizeML(120);
        dto.setQuantity(25);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.save(dto);
        });
    }

    @Test
    @DisplayName("Throw exception for updating none existing product")
    void testForNonExistingProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("None Existing Product");
        dto.setBrand("SublimeNoir");
        dto.setPrice(150);
        dto.setSizeML(130);
        dto.setQuantity(50);

        assertThrows(ProductNotFoundException.class, () -> {
            productService.update(999L,dto);
        });
    }
}