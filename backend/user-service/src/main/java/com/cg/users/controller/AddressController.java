package com.cg.users.controller;

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

import com.cg.users.dto.AddressDto;
import com.cg.users.service.AddressService;
import com.cg.users.util.ResponseUtil;

@RestController
@RequestMapping("/user/address")
public class AddressController {
	private static final String BASE_URL = "/api/user/address";

	@Autowired
	private AddressService service;
	@Autowired
	private ResponseUtil responseUtil;

	// get
	@GetMapping("/{userId}")
	public ResponseEntity<?> get(@PathVariable Long userId) {
		return responseUtil.createSuccessResponse(service.get(userId), BASE_URL + "/" + userId, HttpStatus.OK);
	}

	@GetMapping("/{userId}/{addressId}")
	public ResponseEntity<?> get(@PathVariable Long userId, @PathVariable Long addressId) {
		return responseUtil.createSuccessResponse(service.get(userId, addressId),
				BASE_URL + "/" + userId + "/" + addressId, HttpStatus.OK);
	}

	// add address
	@PostMapping("/{userId}")
	public ResponseEntity<?> add(@RequestBody AddressDto addressDto, @PathVariable Long userId) {
		return responseUtil.createSuccessResponse(service.add(addressDto, userId), BASE_URL + "/" + userId,
				HttpStatus.CREATED);
	}

	// update address
	@PutMapping("/{userId}/{addressId}")
	public ResponseEntity<?> update(@RequestBody AddressDto addressDto, @PathVariable Long userId,
			@PathVariable Long addressId) {
		addressDto.setAddressId(addressId);
		return responseUtil.createSuccessResponse(service.update(addressDto, userId),
				BASE_URL + "/" + userId + "/" + addressId, HttpStatus.ACCEPTED);
	}

	// delete address
	@DeleteMapping("/{userId}/{addressId}")
	public ResponseEntity<?> delete(@PathVariable Long userId, @PathVariable Long addressId) {
		return responseUtil.createSuccessResponse(service.delete(userId, addressId),
				BASE_URL + "/" + userId + "/" + addressId, HttpStatus.ACCEPTED);
	}

}
