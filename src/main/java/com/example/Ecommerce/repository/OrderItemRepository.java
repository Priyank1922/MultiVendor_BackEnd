package com.example.Ecommerce.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.entity.OrderItems;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
Page<OrderItems> findAll(Pageable pageable);
}