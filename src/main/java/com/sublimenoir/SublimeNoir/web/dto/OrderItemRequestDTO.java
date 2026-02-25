package com.sublimenoir.SublimeNoir.web.dto;

import lombok.Data;

@Data
public class OrderItemRequestDTO {

    private Long productId;
    private int quantity;

}