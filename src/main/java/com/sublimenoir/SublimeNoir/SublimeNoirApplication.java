package com.sublimenoir.SublimeNoir;

import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;
import com.sublimenoir.SublimeNoir.domain.entity.Product;
import com.sublimenoir.SublimeNoir.domain.entity.User;
import com.sublimenoir.SublimeNoir.service.interfaces.OrderService;
import com.sublimenoir.SublimeNoir.service.interfaces.ProductService;
import com.sublimenoir.SublimeNoir.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
public class SublimeNoirApplication implements CommandLineRunner {

	private final UserService userService;
	private final OrderService orderService;
	private final ProductService productService;


	private static final Logger logger = LoggerFactory.getLogger(SublimeNoirApplication.class);

	public SublimeNoirApplication(UserService userService, OrderService orderService, ProductService productService) {

		this.userService = userService;
		this.orderService = orderService;
		this.productService = productService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SublimeNoirApplication.class, args);
		logger.info("Application started");
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		// --- Log all Products to console
		logger.info("Products:");
		for (Product product : productService.findAll()) {
			logger.info("Name: {}, Brand: {}, Price: {}, Size: {}ml, Quantity: {}",
					product.getName(),
					product.getBrand(),
					product.getPrice(),
					product.getSizeML(),
					product.getQuantity());
		}

		// --- Log all users to console
		logger.info("Users:");
		for (User user : userService.findAll()) {
			logger.info("ID: {}, Username: {}, Email: {}, Name: {} {}",
					user.getId(),
					user.getUsername(),
					user.getEmail(),
					user.getFirstName(),
					user.getLastName());
		}

		// --- Log all Orders to console
		logger.info("Orders:");
		for (Order order : orderService.findAll()) {
			logger.info("Order ID: {}, User: {}, Status: {}, Total: ${}",
					order.getOrderId(),
					order.getUser().getUsername(),
					order.getStatus(),
					orderService.calculateTotal(order.getOrderId()));
		}

		// --- Query Example
		logger.info("Orders by Status SHIPPED");
		for (Order shipped : orderService.findByStatus(OrderStatus.SHIPPED)) {
			logger.info("Order ID: {}, User: {}, Total: ${}",
					shipped.getOrderId(),
					shipped.getUser().getUsername(),
					orderService.calculateTotal(shipped.getOrderId()));
		}
		// --- Fetch user "sean.oconnor" from the database
		User userSean = userService.findByUsername("sean.oconnor");

		logger.info("Orders by User sean.oconnor:");
		for (Order seanOrder : orderService.findByUser(userSean.getId())) {
			logger.info("Order ID: {}, Status: {}, Total: ${}",
					seanOrder.getOrderId(),
					seanOrder.getStatus(),
					orderService.calculateTotal(seanOrder.getOrderId()));
		}
	}
}
