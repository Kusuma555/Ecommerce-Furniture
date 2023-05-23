package com.cg.ads.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductAdDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long adId;
	@NotNull(message = "User id must be valid")
	private Long userId;
	@NotBlank(message = "Product title must not be blank")
	private String title;
	@NotBlank(message = "Product description must not be blank")
	private String description;
	private String remarks;
	@Min(message = "Product price must be greater than 0", value = 1)
	private double price;
	@Min(message = "Product quantity must be greater than 0", value = 1)
	private int quantity;
	@NotNull(message = "Category id must be valid")
	private Long categoryId;
	
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDate createdAt;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDate updatedAt;
	@JsonProperty(access = Access.READ_ONLY)
	private Boolean accepted;

}
