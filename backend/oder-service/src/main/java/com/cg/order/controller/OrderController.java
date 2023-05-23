package com.cg.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.order.dto.OrderDto;
import com.cg.order.entity.OrderStatus;
import com.cg.order.service.OrderService;
import com.cg.order.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class OrderController {
	private static final String BASE_URL = "/api/order";
	@Autowired
	private OrderService service;
	@Autowired
	private ResponseUtil responseUtil;

	@GetMapping("/{orderId}")
	public ResponseEntity<?> get(@PathVariable Long orderId) {
		return responseUtil.createSuccessResponse(service.get(orderId), BASE_URL + "/" + orderId, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getByUser(@PathVariable Long userId) {
		return responseUtil.createSuccessResponse(service.getByUser(userId), BASE_URL + "/user/" + userId,
				HttpStatus.OK);
	}

	@PostMapping("/{userId}")
	public ResponseEntity<?> add(@PathVariable Long userId, @Valid @RequestBody OrderDto dto) {
		dto.setUserId(userId);
		return responseUtil.createSuccessResponse(service.add(dto), BASE_URL + "/" + userId, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/{status}")
	public ResponseEntity<?> updateStatus(@PathVariable Long orderId, @PathVariable OrderStatus status) {
		return responseUtil.createSuccessResponse(service.updateStatus(orderId, status),
				BASE_URL + "/" + orderId + "/" + status, HttpStatus.OK);
	}
}
