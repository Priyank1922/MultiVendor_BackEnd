package com.example.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Ecommerce.entity.Vendors;

public interface VendorRepository extends JpaRepository<Vendors, Long> {

}