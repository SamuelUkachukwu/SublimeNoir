package com.sublimenoir.SublimeNoir.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private String name;
    private String brand;
    private double price;
    private int sizeML;
    private int quantity;
}
