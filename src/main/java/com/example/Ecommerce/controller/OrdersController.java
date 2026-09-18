package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.OrdersDTO;
import com.example.Ecommerce.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService service;

    // Save
    @PostMapping
    public OrdersDTO save(@RequestBody OrdersDTO dto) {
        return service.save(dto);
    }

    // Get All
    @GetMapping
    public List<OrdersDTO> getAll() {
        return service.getAll();
    }

    // Get By Id
    @GetMapping("/{id}")
    public OrdersDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }

}