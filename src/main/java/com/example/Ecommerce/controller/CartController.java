package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.CartDTO;
import com.example.Ecommerce.service.CartService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService service;



    // Save
    @PostMapping
    public CartDTO save(@RequestBody CartDTO dto) {

        return service.save(dto);

    }



    // Get All
    @GetMapping
    public List<CartDTO> getAll() {

        return service.getAll();

    }



    // Get By Id
    @GetMapping("/{id}")
    public CartDTO getById(@PathVariable Long id) {

        return service.getById(id);

    }



    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        return service.delete(id);

    }

}