package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.CategoryDTO;
import com.example.Ecommerce.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;


    // Save
    @PostMapping
    public CategoryDTO save(@RequestBody CategoryDTO dto) {
        return service.save(dto);
    }


    // Get All
    @GetMapping
    public List<CategoryDTO> getAll() {
        return service.getAll();
    }


    // Get By Id
    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }


    // Update
    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id,
                              @RequestBody CategoryDTO dto) {

        return service.update(id, dto);
    }


    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }


    // Sorting
    @GetMapping("/sort/{field}")
    public List<CategoryDTO> sorting(@PathVariable String field) {

        return service.sorting(field);
    }


    // Pagination
    @GetMapping("/page")
    public Page<CategoryDTO> findPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getAll(page, size);
    }

}