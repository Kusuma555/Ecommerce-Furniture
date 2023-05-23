package com.cg.ps.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long productId;
	@NotBlank(message = "Product title must not be blank")
	private String title;
	private String description;
	@Min(message = "Product price must be greater than 0",value = 1)
	private Double price;
	private int stock;
	@NotNull(message = "Product category mut not be null")
	private Long categoryId;
	
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime createdAt;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime updatedAt;
}
