package com.example.Ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.ClassDto.VendorDTO;
import com.example.Ecommerce.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	private VendorService service;

	// Save
	@PostMapping
	public VendorDTO save(@RequestBody VendorDTO dto) {
		return service.save(dto);
	}

	// Get All
	@GetMapping
	public List<VendorDTO> getAll() {
		return service.getAll();
	}

	// Get By Id
	@GetMapping("/{id}")
	public VendorDTO getById(@PathVariable Long id) {
		return service.getById(id);
	}

	// Update
	@PutMapping("/{id}")
	public VendorDTO update(@PathVariable Long id, @RequestBody VendorDTO dto) {
		return service.update(id, dto);
	}

	// Delete
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		return service.delete(id);
	}

	// Sorting
	@GetMapping("/sort/{field}")
	public List<VendorDTO> sorting(@PathVariable String field) {
		return service.sorting(field);
	}

	// Pagination
	@GetMapping("/page")
	public Page<VendorDTO> findPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		return service.getAll(page, size);
	}

}