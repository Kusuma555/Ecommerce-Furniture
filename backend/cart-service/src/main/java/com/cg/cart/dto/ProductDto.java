package com.cg.cart.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private Long productId;
	@Min(message = "Quantity must be greater than 0", value = 1)
	private Integer quantity;
	@JsonProperty(access = Access.READ_ONLY)
	private Date updatedAt;
}
