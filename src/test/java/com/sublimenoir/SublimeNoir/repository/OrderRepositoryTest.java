package com.sublimenoir.SublimeNoir.repository;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;
import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.domain.entity.User;
import com.sublimenoir.SublimeNoir.domain.repository.OrderRepository;
import com.sublimenoir.SublimeNoir.domain.repository.ProductRepository;
import com.sublimenoir.SublimeNoir.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @Test
    void testShouldSaveOrder() {
        User user = userRepository.save(
                new User("Mace", "macejd@email.com", "James", "Dawn")
        );

        Order order = new Order(user);
        Order saved = orderRepository.save(order);
        assertNotNull(saved.getOrderId());
    }

    @Test
    void testShouldFindByUser() {
        User user = userRepository.save(
                new User("Anna", "anna@email.com", "Anna", "Madueke")
        );

        orderRepository.save(new Order(user));
        List<Order> orders = orderRepository.findByUser(user);

        assertFalse(orders.isEmpty());
    }

    @Test
    void testShouldFindByStatus() {
        User user = userRepository.save(
                new User("mike23", "mike23@email.com", "Mike", "Tyler")
        );

        Order order = new Order(user);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        List<Order> results = orderRepository.findByStatus(OrderStatus.PENDING);

        assertEquals(1, results.size());
    }

    @Test
    void shouldUpdateOrderStatus() {
        User user = userRepository.save(
                new User("Samuel", "sam@email.com", "Samuel", "Ukachukwu")
        );

        Order order = orderRepository.save(new Order(user));
        order.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);

        Order updated = orderRepository.findById(order.getOrderId()).get();
        assertEquals(OrderStatus.SHIPPED, updated.getStatus());
    }

    @Test
    void testShouldDeleteOrder() {
        User user = userRepository.save(
                new User("Odo", "odo11@email.com", "Odogwu", "Ani")
        );

        Order order = orderRepository.save(new Order(user));
        orderRepository.delete(order);

        assertTrue(orderRepository.findById(order.getOrderId()).isEmpty());
    }

    @Test
    void testShouldAddItemToOrder() {
        User user = userRepository.save(
                new User("Chinwe", "ezec@email.com", "Chinwe", "Eze")
        );

        Product product = productRepository.save(
                new Product("Black Adder", "SublimeNoir", 100, 40, 5)
        );

        Order order = new Order(user);
        order.addItem(product, 2);
        orderRepository.save(order);
        Order saved = orderRepository.findById(order.getOrderId()).get();
        assertEquals(1, saved.getItems().size());
    }
}