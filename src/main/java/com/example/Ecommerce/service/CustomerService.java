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

import com.example.Ecommerce.ClassDto.CustomerDTO;
import com.example.Ecommerce.entity.Customers;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    // ================= Entity -> DTO =================

    public CustomerDTO convertToDTO(Customers customer) {

        CustomerDTO dto = new CustomerDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPassword(customer.getPassword());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());

        return dto;
    }

    // ================= DTO -> Entity =================

    public Customers convertToEntity(CustomerDTO dto) {

        Customers customer = new Customers();

        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        return customer;
    }

    // ================= Save =================

    @CacheEvict(value = "CustomerService", allEntries = true)
    public CustomerDTO save(CustomerDTO dto) {

        Customers customer = convertToEntity(dto);

        Customers savedCustomer = repo.save(customer);

        return convertToDTO(savedCustomer);
    }

    // ================= Get All =================

    @Cacheable("CustomerService")
    public List<CustomerDTO> getAll() {

        List<Customers> customers = repo.findAll();

        List<CustomerDTO> dtoList = new ArrayList<>();

        for (Customers customer : customers) {

            dtoList.add(convertToDTO(customer));

        }

        return dtoList;
    }

    // ================= Get By Id =================

    @Cacheable(value = "CustomerService", key = "#id")
    public CustomerDTO getById(Long id) {

        Customers customer = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found : " + id));

        return convertToDTO(customer);
    }

    // ================= Update =================

    @CacheEvict(value = "CustomerService", allEntries = true)
    public CustomerDTO update(Long id, CustomerDTO dto) {

        Customers customer = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found : " + id));

        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        Customers updatedCustomer = repo.save(customer);

        return convertToDTO(updatedCustomer);
    }

    // ================= Delete =================

    @CacheEvict(value = "CustomerService", allEntries = true)
    public String delete(Long id) {

        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found : " + id));

        repo.deleteById(id);

        return "Customer Deleted Successfully";
    }

    // ================= Sorting =================

	@Cacheable(value = "customers", key = "#field")

    public List<CustomerDTO> sorting(String field) {

        List<Customers> customers = repo.findAll(Sort.by(field));

        List<CustomerDTO> dtoList = new ArrayList<>();

        for (Customers customer : customers) {

            dtoList.add(convertToDTO(customer));

        }

        return dtoList;
    }

    // ================= Pagination =================

    public Page<CustomerDTO> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Customers> customerPage = repo.findAll(pageable);

        return customerPage.map(this::convertToDTO);
    }

}