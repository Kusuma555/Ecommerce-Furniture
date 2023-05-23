package com.cg.cart.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long cartId;
	@NotNull(message="UserId must be valid")
	private Long userId;
	private List<ProductDto> products = new ArrayList<>();

}
