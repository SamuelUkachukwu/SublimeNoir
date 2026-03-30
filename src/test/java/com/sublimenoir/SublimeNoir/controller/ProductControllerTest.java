package com.sublimenoir.SublimeNoir.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /api/products creates product")
    void createProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                        .content("{\"name\":\"Shamrock Green\",\"brand\":\"SublimeNoir\",\"price\":120,\"sizeML\":100,\"quantity\":30}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Shamrock Green"))
                .andExpect(jsonPath("$.brand").value("SublimeNoir"));
    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProductById() throws Exception {
        String response = mockMvc.perform(post("/api/products")
                        .content("{\"name\":\"Gateway\",\"brand\":\"SublimeMagic\",\"price\":200,\"sizeML\":120,\"quantity\":50}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String id = response.split("\"productId\":")[1].split(",")[0];

        mockMvc.perform(get("/api/products/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(Integer.parseInt(id)));
    }

    @Test
    void productNotFound() throws Exception {
        mockMvc.perform(get("/api/products/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProduct() throws Exception {
        String response = mockMvc.perform(post("/api/products")
                        .content("{\"name\":\"Bloom\",\"brand\":\"SublimeNoir\",\"price\":150,\"sizeML\":120,\"quantity\":40}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String id = response.split("\"productId\":")[1].split(",")[0];

        mockMvc.perform(put("/api/products/" + id)
                        .content("{\"name\":\"MoonBeam\",\"brand\":\"SublimeNoir\",\"price\":150,\"sizeML\":120,\"quantity\":40}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MoonBeam"));
    }

    @Test
    void deleteProduct() throws Exception {
        String response = mockMvc.perform(post("/api/products")
                        .content("{\"name\":\"MoonBeam\",\"brand\":\"SublimeNoir\",\"price\":150,\"sizeML\":120,\"quantity\":40}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String id = response.split("\"productId\":")[1].split(",")[0];

        mockMvc.perform(delete("/api/products/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}