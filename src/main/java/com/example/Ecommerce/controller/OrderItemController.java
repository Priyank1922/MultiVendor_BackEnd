package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.OrderItemDTO;
import com.example.Ecommerce.service.OrderItemService;

@RestController
@RequestMapping("/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemService service;


    // Save
    @PostMapping
    public OrderItemDTO save(@RequestBody OrderItemDTO dto) {
        return service.save(dto);
    }


    // Get All
    @GetMapping
    public List<OrderItemDTO> getAll() {
        return service.getAll();
    }


    // Get By Id
    @GetMapping("/{id}")
    public OrderItemDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }


    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }


    // Pagination
    @GetMapping("/page")
    public Page<OrderItemDTO> findPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getAll(page, size);
    }

}