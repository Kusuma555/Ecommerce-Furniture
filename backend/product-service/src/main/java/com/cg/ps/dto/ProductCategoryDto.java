package com.cg.ps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductCategoryDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long categoryId;
	@NotBlank(message = "Name must not be blank")
	private String name;
}
