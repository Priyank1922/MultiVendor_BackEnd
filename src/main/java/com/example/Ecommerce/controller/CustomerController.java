package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.CustomerDTO;
import com.example.Ecommerce.service.CustomerService;
@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    // Save
    @PostMapping
    public CustomerDTO save(@RequestBody CustomerDTO dto) {
        return service.save(dto);
    }

    // Get All
    @GetMapping
    public List<CustomerDTO> getAll() {
        return service.getAll();
    }

    // Get By Id
    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // Update
    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable Long id,
                              @RequestBody CustomerDTO dto) {
        return service.update(id, dto);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }

    // Sorting
    @GetMapping("/sort/{field}")
    public List<CustomerDTO> sorting(@PathVariable String field) {
        return service.sorting(field);
    }

    // Pagination
    @GetMapping("/page")
    public Page<CustomerDTO> findPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getAll(page, size);
    }

}