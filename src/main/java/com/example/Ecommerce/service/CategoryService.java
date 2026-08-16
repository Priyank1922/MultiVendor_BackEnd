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

import com.example.Ecommerce.ClassDto.CategoryDTO;
import com.example.Ecommerce.entity.Categorys;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;


    // Entity To DTO

    public CategoryDTO convertToDTO(Categorys category) {

        CategoryDTO dto = new CategoryDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }



    // DTO To Entity

    public Categorys convertToEntity(CategoryDTO dto) {

        Categorys category = new Categorys();

        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return category;
    }




    // Save

    @CacheEvict(value = "CategoryService", allEntries = true)
    public CategoryDTO save(CategoryDTO dto) {

        Categorys category = convertToEntity(dto);

        Categorys savedCategory = repo.save(category);

        return convertToDTO(savedCategory);
    }





    // Get All

    @Cacheable(value = "CategoryService")
    public List<CategoryDTO> getAll() {

        List<Categorys> categories = repo.findAll();

        List<CategoryDTO> dtoList = new ArrayList<>();

        for(Categorys category : categories) {

            dtoList.add(convertToDTO(category));

        }

        return dtoList;
    }





    // Get By Id

    @Cacheable(value = "CategoryService", key = "#id")
    public CategoryDTO getById(Long id) {

        Categorys category = repo.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Category Not Found : " + id));


        return convertToDTO(category);
    }





    // Update

    @CacheEvict(value = "CategoryService", allEntries = true)
    public CategoryDTO update(Long id, CategoryDTO dto) {


        Categorys category = repo.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Category Not Found : " + id));


        category.setName(dto.getName());
        category.setDescription(dto.getDescription());


        Categorys updatedCategory = repo.save(category);


        return convertToDTO(updatedCategory);

    }





    // Delete

    @CacheEvict(value = "CategoryService", allEntries = true)
    public String delete(Long id) {


        repo.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Category Not Found : " + id));


        repo.deleteById(id);


        return "Category Deleted Successfully";
    }





    // Sorting

	@Cacheable(value = "categorys", key = "#field")

    public List<CategoryDTO> sorting(String field) {


        List<Categorys> categories = repo.findAll(Sort.by(field));


        List<CategoryDTO> dtoList = new ArrayList<>();


        for(Categorys category : categories) {

            dtoList.add(convertToDTO(category));

        }


        return dtoList;
    }





    // Pagination

    public Page<CategoryDTO> getAll(int page, int size) {


        Pageable pageable = PageRequest.of(page, size);


        Page<Categorys> categoryPage = repo.findAll(pageable);


        return categoryPage.map(this::convertToDTO);

    }

}