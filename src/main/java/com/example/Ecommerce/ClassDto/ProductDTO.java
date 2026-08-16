package com.example.Ecommerce.ClassDto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;


    private Long categoryId;
    private String categoryName;

    private Long vendorId;
    private String vendorName;
}