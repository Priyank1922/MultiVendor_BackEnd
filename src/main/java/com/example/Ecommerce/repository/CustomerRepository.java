package com.example.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface CustomerRepository extends JpaRepository<Customers, Long> {

}