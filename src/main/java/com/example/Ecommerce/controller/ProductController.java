package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.ProductDTO;
import com.example.Ecommerce.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService service;
	
	
	

	@PostMapping
	public ProductDTO save(@RequestBody ProductDTO dto) {
		return service.save(dto);
	}

	@GetMapping
	public List<ProductDTO> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public ProductDTO getById(@PathVariable Long id) {
	
		
		return service.getById(id);
	}

	@PutMapping("/{id}")
	public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
		return service.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		return service.delete(id);
	}

	@GetMapping("/sort/{field}")
	public List<ProductDTO> sorting(@PathVariable String field) {
		return service.sorting(field);
	}

	@GetMapping("/search")
	public List<ProductDTO> search(@RequestParam String name) {
		return service.search(name);
	}

	@GetMapping("/page")
	public Page<ProductDTO> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		return service.getAll(page, size);
	}

}