package com.cg.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.gateway.service.RemoteOrderService;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class RemoteOrderController {
	@Autowired
	private RemoteOrderService service;

	@GetMapping("/{orderId}")
	public ResponseEntity<?> get(@PathVariable Long orderId) {
		return new ResponseEntity<>(service.get(orderId), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getByUser(@PathVariable Long userId) {
		return new ResponseEntity<>(service.getByUser(userId), HttpStatus.OK);
	}

	@PostMapping("/{userId}")
	public ResponseEntity<?> add(@PathVariable Long userId, @RequestBody Object dto) {

		return new ResponseEntity<>(service.add(userId, dto), HttpStatus.OK);
	}

	@PutMapping("/{orderId}/{status}")
	public ResponseEntity<?> updateStatus(@PathVariable Long orderId, @PathVariable Object status) {
		return new ResponseEntity<>(service.updateStatus(orderId, status), HttpStatus.OK);
	}
}
