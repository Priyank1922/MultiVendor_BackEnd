package com.example.Ecommerce.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Carts;

public interface CartRepository extends JpaRepository<Carts, Long> {

	Page<Carts> findAll(Pageable pageable);

}