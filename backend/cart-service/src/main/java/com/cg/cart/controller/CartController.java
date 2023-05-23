package com.cg.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.cart.dto.ProductDto;
import com.cg.cart.service.CartService;
import com.cg.cart.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {
	private static final String BASE_URL = "/api/cart";
	@Autowired
	private CartService service;
	@Autowired
	private ResponseUtil responseUtil;
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> get(@PathVariable Long userId) {
		return responseUtil.createSuccessResponse(service.get(userId), BASE_URL + "/" + userId,
				HttpStatus.OK);
	}


	@PostMapping("/{userId}")
	public ResponseEntity<?> update(@PathVariable Long userId, @Valid @RequestBody ProductDto dto) {
		return responseUtil.createSuccessResponse(service.update(userId, dto), BASE_URL + "/" + userId,
				HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{userId}/{productId}")
	public ResponseEntity<?> remove(@PathVariable Long userId, @PathVariable Long productId) {
		return responseUtil.createSuccessResponse(service.remove(userId, productId),
				BASE_URL + "/" + userId + "/" + productId, HttpStatus.ACCEPTED);
	}
}
