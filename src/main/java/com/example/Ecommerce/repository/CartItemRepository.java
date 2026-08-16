package com.example.Ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.entity.CartItems;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {

    Page<CartItems> findAll(Pageable pageable);

}