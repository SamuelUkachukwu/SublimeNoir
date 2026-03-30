package com.sublimenoir.SublimeNoir.service;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.exception.OrderNotFoundException;
import com.sublimenoir.SublimeNoir.exception.ProductNotFoundException;
import com.sublimenoir.SublimeNoir.service.impl.OrderServiceImpl;
import com.sublimenoir.SublimeNoir.service.impl.ProductServiceImpl;
import com.sublimenoir.SublimeNoir.service.impl.UserServiceImpl;
import com.sublimenoir.SublimeNoir.web.dto.OrderRequestDTO;
import com.sublimenoir.SublimeNoir.web.dto.ProductRequestDTO;
import com.sublimenoir.SublimeNoir.web.dto.UserRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProductServiceImpl productService;

    private Long createUser() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername("Mark");
        dto.setEmail("mark@email.com");
        dto.setFirstName("Mark");
        dto.setLastName("Brown");

        return userService.save(dto).getId();
    }

    private Long createProduct(int quantity) {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Dawn");
        dto.setBrand("SublimeNoir");
        dto.setPrice(130);
        dto.setSizeML(70);
        dto.setQuantity(quantity);

        return productService.save(dto).getProductId();
    }

    @Test
    @DisplayName("Should test if order is created")
    void testCreateOrder() {
        Long userId = createUser();

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setUserId(userId);

        Order order = orderService.createOrder(dto);
        assertNotNull(order.getOrderId());
    }

    @Test
    @DisplayName("Should test adding product to order")
    void testAddProductToOrder() {
        Long userId = createUser();
        Long productId = createProduct(10);

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setUserId(userId);

        Order order = orderService.createOrder(dto);
        Order updated = orderService.addProduct(order.getOrderId(), productId, 2);
        assertEquals(1, updated.getItems().size());
    }

    @Test
    @DisplayName("Test should remove product from order")
    void testRemoveProductFromOrder() {
        Long userId = createUser();
        Long productId = createProduct(10);

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setUserId(userId);
        Order order = orderService.createOrder(dto);
        orderService.addProduct(order.getOrderId(), productId, 2);

        Order updated = orderService.removeProduct(order.getOrderId(), productId);
        assertEquals(0, updated.getItems().size());
    }

    @Test
    @DisplayName("Throw order not found exception")
    void testOrderNotFound() {
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.addProduct(999L, 1L, 2);
        });
    }

    @Test
    @DisplayName("Throw exception when product not found")
    void testProductNotFound() {
        Long userId = createUser();

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setUserId(userId);
        Order order = orderService.createOrder(dto);

        assertThrows(ProductNotFoundException.class, () -> {
            orderService.addProduct(order.getOrderId(), 999L, 2);
        });
    }

    @Test
    @DisplayName("Throw an exception when not enough stock")
    void testNotEnoughStock() {
        Long userId = createUser();
        Long productId = createProduct(1);

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setUserId(userId);
        Order order = orderService.createOrder(dto);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.addProduct(order.getOrderId(), productId, 5);
        });
    }
}