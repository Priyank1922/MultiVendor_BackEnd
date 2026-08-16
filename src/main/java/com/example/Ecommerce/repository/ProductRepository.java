package com.example.Ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Ecommerce.entity.Products;

import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Products, Long> {

	List<Products> findByNameContainingIgnoreCase(String name);

	@Query("SELECT p FROM Products p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Products> searchByName(@Param("name") String name);

}