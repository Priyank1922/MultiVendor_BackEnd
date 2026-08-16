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

import com.example.Ecommerce.entity.Carts;
import com.example.Ecommerce.ClassDto.CartItemDTO;
import com.example.Ecommerce.entity.CartItems;
import com.example.Ecommerce.entity.Products;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.CartItemRepository;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.repository.ProductRepository;

@Service
public class CartItemService {

	@Autowired
	private CartItemRepository repo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private ProductRepository productRepo;

	// Entity To DTO

	public CartItemDTO convertToDTO(CartItems cartItem) {

		CartItemDTO dto = new CartItemDTO();

		dto.setId(cartItem.getId());
		dto.setQuantity(cartItem.getQuantity());

		if (cartItem.getCart() != null) {

			dto.setCartId(cartItem.getCart().getId());

		}

		if (cartItem.getProduct() != null) {

			dto.setProductId(cartItem.getProduct().getId());

		}

		return dto;
	}

	// DTO To Entity

	public CartItems convertToEntity(CartItemDTO dto) {

		CartItems cartItem = new CartItems();

		cartItem.setId(dto.getId());
		cartItem.setQuantity(dto.getQuantity());

		if (dto.getCartId() != null) {

			Carts cart = cartRepo.findById(dto.getCartId())
					.orElseThrow(() -> new ResourceNotFoundException("Cart Not Found : " + dto.getCartId()));

			cartItem.setCart(cart);

		}

		if (dto.getProductId() != null) {

			Products product = productRepo.findById(dto.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException("Product Not Found : " + dto.getProductId()));

			cartItem.setProduct(product);

		}

		return cartItem;

	}

	// Pagination

	public Page<CartItemDTO> getAll(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<CartItems> cartItemPage = repo.findAll(pageable);

		return cartItemPage.map(this::convertToDTO);

	}

	// Save

	@CacheEvict(value = "CartltemService", allEntries = true)
	public CartItemDTO save(CartItemDTO dto) {

		CartItems cartItem = convertToEntity(dto);

		if (cartItem.getDeleted() == null) {

			cartItem.setDeleted(false);

		}

		CartItems saved = repo.save(cartItem);

		return convertToDTO(saved);

	}


	
	
	
	@Cacheable(value = "CartltemService")
	public List<CartItemDTO> getAll() {

		List<CartItems> cartItems = repo.findAll();

		List<CartItemDTO> dtoList = new ArrayList<>();

		for (CartItems item : cartItems) {

			dtoList.add(convertToDTO(item));

		}

		return dtoList;

	}


	
	
	
	
	@Cacheable(value = "CartltemService", key = "#id")
	public CartItemDTO getById(Long id) {

		CartItems item = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart Item Not Found : " + id));

		return convertToDTO(item);

	}
	
	
	
	
	

	@CacheEvict(value = "CartltemService", allEntries = true)
	public CartItemDTO update(Long id, CartItemDTO dto) {

		CartItems cartItem = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart Item Not Found : " + id));

		cartItem.setQuantity(dto.getQuantity());

		if (dto.getCartId() != null) {

			Carts cart = cartRepo.findById(dto.getCartId())
					.orElseThrow(() -> new ResourceNotFoundException("Cart Not Found : " + dto.getCartId()));

			cartItem.setCart(cart);
		}

		if (dto.getProductId() != null) {

			Products product = productRepo.findById(dto.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException("Product Not Found : " + dto.getProductId()));

			cartItem.setProduct(product);
		}

		CartItems updated = repo.save(cartItem);

		return convertToDTO(updated);
		
		
	}
	
	

	@CacheEvict(value = "CartltemService", allEntries = true)
	public String delete(Long id) {

		repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart Item Not Found : " + id));

		repo.deleteById(id);

		return "Cart Item Deleted Successfully";

	}

}