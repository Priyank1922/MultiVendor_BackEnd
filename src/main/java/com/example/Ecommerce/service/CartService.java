package com.example.Ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.ClassDto.CartDTO;
import com.example.Ecommerce.entity.Carts;
import com.example.Ecommerce.entity.Customers;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.repository.CustomerRepository;


@Service
public class CartService {


    @Autowired
    private CartRepository repo;


    @Autowired
    private CustomerRepository customerRepo;



    // Entity To DTO

    public CartDTO convertToDTO(Carts cart) {

        CartDTO dto = new CartDTO();

        dto.setId(cart.getId());

        if(cart.getCustomer() != null) {
            dto.setCustomerId(cart.getCustomer().getId());
        }

        return dto;
    }




    // DTO To Entity

    public Carts convertToEntity(CartDTO dto) {

        Carts cart = new Carts();

        cart.setId(dto.getId());


        if(dto.getCustomerId() != null) {

            Customers customer = customerRepo.findById(dto.getCustomerId())
                    .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Customer Not Found : " + dto.getCustomerId()));


            cart.setCustomer(customer);
        }


        return cart;
    }




    // Pagination

    public Page<CartDTO> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Carts> cartPage = repo.findAll(pageable);

        return cartPage.map(this::convertToDTO);
    }




    // Save

    @CacheEvict(value = "CartService", allEntries = true)
    public CartDTO save(CartDTO dto) {


        Carts cart = convertToEntity(dto);


        if(cart.getDeleted() == null) {
            cart.setDeleted(false);
        }


        Carts savedCart = repo.save(cart);


        return convertToDTO(savedCart);
    }




    // Get All

    @Cacheable(value = "CartService")
    public List<CartDTO> getAll() {


        List<Carts> carts = repo.findAll();


        List<CartDTO> dtoList = new ArrayList<>();


        for(Carts cart : carts) {

            dtoList.add(convertToDTO(cart));

        }


        return dtoList;
    }




    // Get By Id

    @Cacheable(value = "CartService", key = "#id")
    public CartDTO getById(Long id) {


        Carts cart = repo.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Cart Not Found : " + id));


        return convertToDTO(cart);
    }




    // Delete

    @CacheEvict(value = "CartService", allEntries = true)
    public String delete(Long id) {


        repo.findById(id)
        .orElseThrow(() ->
        new ResourceNotFoundException(
        "Cart Not Found : " + id));


        repo.deleteById(id);


        return "Cart Deleted Successfully";
    }

}