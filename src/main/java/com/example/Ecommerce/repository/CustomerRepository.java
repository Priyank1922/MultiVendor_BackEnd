package com.example.Ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Customers;

public interface CustomerRepository extends JpaRepository<Customers, Long> {

    Optional<Customers> findByEmail(String email);

    boolean existsByEmail(String email);
}