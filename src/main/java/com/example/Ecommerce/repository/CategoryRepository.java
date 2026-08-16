package com.example.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.Categorys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface CategoryRepository extends JpaRepository<Categorys, Long> {
	
	Page<Categorys> findAll(Pageable pageable);

}