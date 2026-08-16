
package com.example.Ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.ClassDto.*;
import com.example.Ecommerce.entity.Categorys;
import com.example.Ecommerce.entity.Products;
import com.example.Ecommerce.entity.Vendors;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.repository.ProductRepository;
import com.example.Ecommerce.repository.VendorRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private VendorRepository vendorRepo;

	// SAVE
	@CacheEvict(value = "ProductService", allEntries = true)

	public ProductDTO save(ProductDTO dto) {

		Categorys category = categoryRepo.findById(dto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

		Vendors vendor = vendorRepo.findById(dto.getVendorId())
				.orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found"));

		Products product = new Products();

		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setStock(dto.getStock());
		product.setCategory(category);
		product.setVendor(vendor);

		Products saved = repo.save(product);

		return convertToDTO(saved);
	}

	// GET ALL
	@Cacheable("ProductService")
	public List<ProductDTO> getAll() {

		return repo.findAll().stream().map(this::convertToDTO).toList();
	}

	// GET BY ID
	@Cacheable(value = "ProductService", key = "#id")
	public ProductDTO getById(Long id) {

		Products product = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found : " + id));

		return convertToDTO(product);
	}

	// UPDATE
	@CacheEvict(value = "ProductService", allEntries = true)
	public ProductDTO update(Long id, ProductDTO dto) {

	    Products product = repo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Product Not Found : " + id));

	    product.setName(dto.getName());
	    product.setDescription(dto.getDescription());
	    product.setPrice(dto.getPrice());
	    product.setStock(dto.getStock());

	    if (dto.getCategoryId() != null) {
	        Categorys category = categoryRepo.findById(dto.getCategoryId())
	                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
	        product.setCategory(category);
	    }

	    if (dto.getVendorId() != null) {
	        Vendors vendor = vendorRepo.findById(dto.getVendorId())
	                .orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found"));
	        product.setVendor(vendor);
	    }

	    Products updated = repo.save(product);

	    return convertToDTO(updated);
	} 

	@CacheEvict(value = "ProductService", allEntries = true)
	public String delete(Long id) {

		repo.deleteById(id);

		return "Product Deleted Successfully";
	}
	
	

	public Page<ProductDTO> getAll(int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);

	    Page<Products> products = repo.findAll(pageable);

	    return products.map(this::convertToDTO);
	}

	// SORTING
	@Cacheable(value = "products", key = "#field")

	public List<ProductDTO> sorting(String field) {

		List<Products> products = repo.findAll(Sort.by(field));

		List<ProductDTO> dtoList = new ArrayList<>();

		for (Products product : products) {
			dtoList.add(convertToDTO(product));
		}

		return dtoList;
	}

	
	// SEARCH
	public List<ProductDTO> search(String name) {

		List<Products> products = repo.findByNameContainingIgnoreCase(name);
		  

		List<ProductDTO> dtoList = new ArrayList<>();

		for (Products product : products) {
			dtoList.add(convertToDTO(product));
		}

		return dtoList;
	}

	private ProductDTO convertToDTO(Products product) {

	    ProductDTO dto = new ProductDTO();

	    dto.setId(product.getId());
	    dto.setName(product.getName());
	    dto.setDescription(product.getDescription());
	    dto.setPrice(product.getPrice());
	    dto.setStock(product.getStock());

	    dto.setCategoryId(product.getCategory().getId());
	    dto.setCategoryName(product.getCategory().getName());

	    dto.setVendorId(product.getVendor().getId());
	    dto.setVendorName(product.getVendor().getName());

	    return dto;
	}
}