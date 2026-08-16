
	package com.example.Ecommerce.entity;

	import java.math.BigDecimal;

	import org.hibernate.annotations.SQLDelete;
	import org.hibernate.annotations.SQLRestriction;

	import com.fasterxml.jackson.annotation.JsonIgnore;

	import jakarta.persistence.*;
	import jakarta.validation.constraints.Positive;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Entity
	@Table(name = "order_items")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SQLDelete(sql = "UPDATE order_items SET deleted = true WHERE id=?")
	@SQLRestriction("deleted=false")
	public class OrderItems {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Boolean deleted = false;


	    @Positive(message = "Quantity must be greater than zero")
	    private Integer quantity;


	    @Positive(message = "Price must be greater than zero")
	    private BigDecimal price;


	    @ManyToOne    
	    @JoinColumn(name = "order_id")
	    @JsonIgnore
	    private Orders orders;


	    @ManyToOne
	    @JoinColumn(name = "product_id")
	    @JsonIgnore
	    private Products product;

	}
