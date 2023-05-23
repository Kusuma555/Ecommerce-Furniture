package com.cg.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.gateway.service.RemoteAddressService;

@CrossOrigin
@RestController
@RequestMapping("/user/address")
public class RemoteAddressController {
	@Autowired
	private RemoteAddressService service;
	// get
	@GetMapping("/{userId}")
	public ResponseEntity<?> get(@PathVariable Long userId) {
		return new ResponseEntity<>(service.get(userId), HttpStatus.OK);
	}

	@GetMapping("/{userId}/{addressId}")
	public ResponseEntity<?> get(@PathVariable Long userId, @PathVariable Long addressId) {
		return new ResponseEntity<>(service.get(userId, addressId), HttpStatus.OK);
	}

	// add address
	@PostMapping("/{userId}")
	public ResponseEntity<?> add(@RequestBody Object addressDto, @PathVariable Long userId) {
		return new ResponseEntity<>(service.add(addressDto, userId), HttpStatus.CREATED);
	}

	// update address
	@PutMapping("/{userId}/{addressId}")
	public ResponseEntity<?> update(@RequestBody Object addressDto, @PathVariable Long userId,
			@PathVariable Long addressId) {
		return new ResponseEntity<>(service.update(addressDto, userId, addressId), HttpStatus.ACCEPTED);
	}

	// delete address
	@DeleteMapping("/{userId}/{addressId}")
	public ResponseEntity<?> delete(@PathVariable Long userId, @PathVariable Long addressId) {
		return new ResponseEntity<>(service.delete(userId, addressId), HttpStatus.OK);
	}

}
