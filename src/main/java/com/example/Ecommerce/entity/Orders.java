package com.example.Ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE Orders SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")

public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted = false;
    @NotNull
    private LocalDate orderDate;

    @NotNull
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")


    private Customers customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonIgnore

    private List<OrderItems> orderItems = new ArrayList<>();

}
