package com.sublimenoir.SublimeNoir.controller;

import com.sublimenoir.SublimeNoir.service.impl.ProductServiceImpl;
import com.sublimenoir.SublimeNoir.service.impl.UserServiceImpl;
import com.sublimenoir.SublimeNoir.web.dto.ProductRequestDTO;
import com.sublimenoir.SublimeNoir.web.dto.UserRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProductServiceImpl productService;


    private Long createUser() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername("David89");
        dto.setEmail("david89@email.com");
        dto.setFirstName("David");
        dto.setLastName("Onyewo");

        return userService.save(dto).getId();
    }

    private Long createProduct() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Starlight");
        dto.setBrand("SublimeMagic");
        dto.setPrice(140);
        dto.setSizeML(80);
        dto.setQuantity(40);

        return productService.save(dto).getProductId();
    }

    @Test
    @DisplayName("POST /api/orders creates order")
    void testCreateOrder() throws Exception {

        Long userId = createUser();

        mockMvc.perform(post("/api/orders")
                        .content("{\"userId\":" + userId + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/orders returns list")
    void testGetAllOrders() throws Exception {

        mockMvc.perform(get("/api/orders"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/orders/{id} returns order")
    void testGetOrderById() throws Exception {

        Long userId = createUser();

        String response = mockMvc.perform(post("/api/orders")
                        .content("{\"userId\":" + userId + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String id = response.split("\"orderId\":")[1].split(",")[0];

        mockMvc.perform(get("/api/orders/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(Integer.parseInt(id)));
    }

    @Test
    @DisplayName("GET /api/orders/{id} returns 404")
    void testOrderNotFound() throws Exception {

        mockMvc.perform(get("/api/orders/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST add product to order")
    void testAddProductToOrder() throws Exception {

        Long userId = createUser();
        Long productId = createProduct();

        String response = mockMvc.perform(post("/api/orders")
                        .content("{\"userId\":" + userId + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String orderId = response.split("\"orderId\":")[1].split(",")[0];

        mockMvc.perform(post("/api/orders/" + orderId + "/products/" + productId)
                        .param("quantity", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.orderId").value(Integer.parseInt(orderId)));
    }

    @Test
    @DisplayName("PUT change order status")
    void testChangeOrderStatus() throws Exception {

        Long userId = createUser();

        String response = mockMvc.perform(post("/api/orders")
                        .content("{\"userId\":" + userId + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String orderId = response.split("\"orderId\":")[1].split(",")[0];

        mockMvc.perform(put("/api/orders/" + orderId + "/status")
                        .param("status", "SHIPPED"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status").value("SHIPPED"));
    }
}