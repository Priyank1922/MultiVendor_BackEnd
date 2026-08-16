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

import com.example.Ecommerce.ClassDto.OrderItemDTO;
import com.example.Ecommerce.entity.OrderItems;
import com.example.Ecommerce.entity.Orders;
import com.example.Ecommerce.entity.Products;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.OrderItemRepository;
import com.example.Ecommerce.repository.OrdersRepository;
import com.example.Ecommerce.repository.ProductRepository;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository repo;

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private ProductRepository productRepo;


    // Entity To DTO

    public OrderItemDTO convertToDTO(OrderItems orderItem) {

        OrderItemDTO dto = new OrderItemDTO();

        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());

        if(orderItem.getOrders() != null) {
            dto.setOrderId(orderItem.getOrders().getId());
        }

        if(orderItem.getProduct() != null) {
            dto.setProductId(orderItem.getProduct().getId());
        }

        return dto;
    }



    // DTO To Entity

    public OrderItems convertToEntity(OrderItemDTO dto) {

        OrderItems orderItem = new OrderItems();

        orderItem.setId(dto.getId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());


        if(dto.getOrderId() != null) {

            Orders orders = ordersRepo.findById(dto.getOrderId())
                    .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Order Not Found : " + dto.getOrderId()));

            orderItem.setOrders(orders);
        }


        if(dto.getProductId() != null) {

            Products product = productRepo.findById(dto.getProductId())
                    .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Product Not Found : " + dto.getProductId()));

            orderItem.setProduct(product);
        }


        return orderItem;
    }



    // Save

    @CacheEvict(value="OrderItemService", allEntries = true)
    public OrderItemDTO save(OrderItemDTO dto) {

        OrderItems orderItem = convertToEntity(dto);

        OrderItems saved = repo.save(orderItem);

        return convertToDTO(saved);
    }




    // Get All

    @Cacheable(value="OrderItemService")
    public List<OrderItemDTO> getAll() {

        List<OrderItems> list = repo.findAll();

        List<OrderItemDTO> dtoList = new ArrayList<>();

        for(OrderItems item : list) {

            dtoList.add(convertToDTO(item));

        }

        return dtoList;
    }




    // Get By Id

    @Cacheable(value="OrderItemService", key="#id")
    public OrderItemDTO getById(Long id) {


        OrderItems item = repo.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Order Item Not Found : " + id));


        return convertToDTO(item);
    }




    // Delete

    @CacheEvict(value="OrderItemService", allEntries = true)
    public String delete(Long id) {


        repo.findById(id)
        .orElseThrow(() ->
        new ResourceNotFoundException(
        "Order Item Not Found : " + id));


        repo.deleteById(id);


        return "Order Item Deleted Successfully";
    }





    // Pagination

    public Page<OrderItemDTO> getAll(int page, int size) {


        Pageable pageable = PageRequest.of(page, size);


        Page<OrderItems> orderPage = repo.findAll(pageable);


        return orderPage.map(this::convertToDTO);

    }

}