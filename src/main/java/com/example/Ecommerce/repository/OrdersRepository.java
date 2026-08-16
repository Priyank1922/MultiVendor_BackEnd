package com.example.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Orders;
import com.example.Ecommerce.entity.Vendors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	
	Page<Orders> findAll(Pageable pageable);


}