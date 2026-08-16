package com.example.Ecommerce.ClassDto;



	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	
	public class CartItemDTO {

	    private Long id;


	    private Integer quantity;

	    private Long cartId;

	    private Long productId;

	}

