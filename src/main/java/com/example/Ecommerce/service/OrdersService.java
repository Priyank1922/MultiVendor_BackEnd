package com.example.Ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.ClassDto.OrdersDTO;
import com.example.Ecommerce.entity.Customers;
import com.example.Ecommerce.entity.Orders;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CustomerRepository;
import com.example.Ecommerce.repository.OrdersRepository;

@Service
public class OrdersService {

	@Autowired
	private OrdersRepository repo;

	@Autowired
	private CustomerRepository customerRepo;

	// ================= Entity -> DTO =================

	public OrdersDTO convertToDTO(Orders orders) {

		OrdersDTO dto = new OrdersDTO();

		dto.setId(orders.getId());
		dto.setOrderDate(orders.getOrderDate());
		dto.setTotalAmount(orders.getTotalAmount());

		if (orders.getCustomer() != null) {
			dto.setCustomerId(orders.getCustomer().getId());
		}

		return dto;
	}

	// ================= DTO -> Entity =================

	public Orders convertToEntity(OrdersDTO dto) {

		Orders orders = new Orders();

		orders.setId(dto.getId());
		orders.setOrderDate(dto.getOrderDate());
		orders.setTotalAmount(dto.getTotalAmount());

		if (dto.getCustomerId() != null) {

			Customers customer = customerRepo.findById(dto.getCustomerId())
					.orElseThrow(() -> new ResourceNotFoundException("Customer Not Found : " + dto.getCustomerId()));

			orders.setCustomer(customer);
		}

		return orders;
	}

	// ================= Save =================

	@CacheEvict(value = "OrdersService", allEntries = true)
	public OrdersDTO save(OrdersDTO dto) {

		Orders orders = convertToEntity(dto);

		Orders savedOrder = repo.save(orders);

		return convertToDTO(savedOrder);
	}

	// ================= Get All =================

	@Cacheable("OrdersService")
	public List<OrdersDTO> getAll() {

		List<Orders> ordersList = repo.findAll();

		List<OrdersDTO> dtoList = new ArrayList<>();

		for (Orders orders : ordersList) {

			dtoList.add(convertToDTO(orders));

		}

		return dtoList;
	}

	// ================= Get By Id =================

	@Cacheable(value = "OrdersService", key = "#id")
	public OrdersDTO getById(Long id) {

		Orders orders = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found : " + id));

		return convertToDTO(orders);
	}

	// ================= Delete =================

	@CacheEvict(value = "OrdersService", allEntries = true)
	public String delete(Long id) {

		repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found : " + id));

		repo.deleteById(id);

		return "Order Deleted Successfully";
	}

}