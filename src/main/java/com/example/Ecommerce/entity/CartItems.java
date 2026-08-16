package com.example.Ecommerce.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE cart_items SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")

public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean  deleted = false;

    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")

    private Carts  cart;

    @ManyToOne
    @JoinColumn(name = "product_id")


    private Products product;

}