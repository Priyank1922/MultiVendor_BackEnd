package com.example.Ecommerce.ClassDto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;


    private Integer quantity;

    private BigDecimal price;

    private Long orderId;

    private Long productId;

}