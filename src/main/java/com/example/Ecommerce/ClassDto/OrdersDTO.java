package com.example.Ecommerce.ClassDto;


	import java.math.BigDecimal;
	import java.time.LocalDate;

	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	
	public class OrdersDTO {

	    private Long id;


	    private LocalDate orderDate;

	    private BigDecimal totalAmount;

	    private Long customerId;

	}


