package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.CartItemDTO;
import com.example.Ecommerce.service.CartItemService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/cartitem")
public class CartItemController {


    @Autowired
    private CartItemService service;



    // Save
    @PostMapping
    public CartItemDTO save(@RequestBody CartItemDTO dto) {

        return service.save(dto);

    }



    // Get All
    @GetMapping
    public List<CartItemDTO> getAll() {

        return service.getAll();

    }




    // Get By Id
    @GetMapping("/{id}")
    public CartItemDTO getById(@PathVariable Long id) {

        return service.getById(id);

    }




    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        return service.delete(id);

    }

     //update
    @PutMapping("/{id}")
    public CartItemDTO update(@PathVariable Long id,
                              @RequestBody CartItemDTO dto) {

        return service.update(id, dto);
    }

    // Pagination
    @GetMapping("/page")
    public Page<CartItemDTO> findPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        return service.getAll(page, size);

    }

}