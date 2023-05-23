package com.cg.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.gateway.service.RemoteCartService;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class RemoteCartController {
	@Autowired
	private RemoteCartService service;

	
	@GetMapping("/{userId}")
	public ResponseEntity<?> get(@PathVariable Long userId) {
		return new ResponseEntity<>(service.get(userId), HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<?> update(@PathVariable Long userId, @RequestBody Object dto) {
		return new ResponseEntity<>(service.update(userId, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{userId}/{productId}")
	public ResponseEntity<?> remove(@PathVariable Long userId, @PathVariable Long productId) {
		return new ResponseEntity<>(service.remove(userId, productId), HttpStatus.ACCEPTED);
	}
}
