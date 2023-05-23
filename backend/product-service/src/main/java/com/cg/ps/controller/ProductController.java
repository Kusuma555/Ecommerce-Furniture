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

import com.cg.ps.dto.ProductDto;
import com.cg.ps.service.ProductService;
import com.cg.ps.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	private static final String BASE_URL = "/api/product";

	@Autowired
	private ProductService service;
	@Autowired
	private ResponseUtil responseUtil;

	@GetMapping
	public ResponseEntity<?> get() {
		return responseUtil.createSuccessResponse(service.get(), BASE_URL, HttpStatus.OK);
	}

	@GetMapping("/page/{page}")
	public ResponseEntity<?> get(@PathVariable int page) {
		return responseUtil.createSuccessResponse(service.get(page), BASE_URL + "/page/" + page, HttpStatus.OK);
	}

	@GetMapping("/search/{query}/{page}")
	public ResponseEntity<?> get(@PathVariable String query, @PathVariable int page) {
		return responseUtil.createSuccessResponse(service.get(query, page), BASE_URL + "/search/" + query + "/" + page,
				HttpStatus.OK);
	}

	@GetMapping("/sort/{by}/{order}/{page}")
	public ResponseEntity<?> get(@PathVariable String by, @PathVariable String order, @PathVariable int page) {
		return responseUtil.createSuccessResponse(service.get(by, order, page),
				BASE_URL + "/sort/" + by + "/" + order + "/" + page, HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<?> get(@PathVariable Long productId) {
		return responseUtil.createSuccessResponse(service.get(productId), BASE_URL + "/" + productId, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> add(@Valid @RequestBody ProductDto dto) {
		return responseUtil.createSuccessResponse(service.add(dto), BASE_URL, HttpStatus.CREATED);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<?> update(@PathVariable Long productId, @Valid @RequestBody ProductDto dto) {
		dto.setProductId(productId);
		return responseUtil.createSuccessResponse(service.update(dto), BASE_URL + "/" + productId, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<?> delete(@PathVariable Long productId) {
		return responseUtil.createSuccessResponse(service.delete(productId), BASE_URL + "/" + productId, HttpStatus.OK);
	}
}
