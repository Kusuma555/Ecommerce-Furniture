package com.cg.ps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ps.dto.ProductCategoryDto;
import com.cg.ps.service.ProductCategoryService;
import com.cg.ps.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
	private static final String BASE_URL = "/api/category";

	@Autowired
	private ProductCategoryService service;
	@Autowired
	private ResponseUtil responseUtil;

	@GetMapping
	public ResponseEntity<?> get() {
		return responseUtil.createSuccessResponse(service.get(), BASE_URL, HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<?> get(@PathVariable Long categoryId) {
		return responseUtil.createSuccessResponse(service.get(categoryId), BASE_URL + "/" + categoryId, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> add(@Valid @RequestBody ProductCategoryDto dto) {
		return responseUtil.createSuccessResponse(service.add(dto), BASE_URL, HttpStatus.CREATED);
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<?> update(@PathVariable Long categoryId, @Valid @RequestBody ProductCategoryDto dto) {
		dto.setCategoryId(categoryId);
		return responseUtil.createSuccessResponse(service.update(dto), BASE_URL + "/" + categoryId,
				HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> delete(@PathVariable Long categoryId) {
		return responseUtil.createSuccessResponse(service.delete(categoryId), BASE_URL + "/" + categoryId,
				HttpStatus.OK);
	}
}
