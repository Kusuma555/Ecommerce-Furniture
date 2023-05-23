package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user-service-address", url = "http://localhost:8000/api/user/address")
public interface RemoteAddressService {
	//get
	@GetMapping("/{userId}")
	Object get(@PathVariable Long userId);

	@GetMapping("/{userId}/{addressId}")
	Object get(@PathVariable Long userId, @PathVariable Long addressId);

	// add address
	@PostMapping("/{userId}")
	Object add(Object addressDto, @PathVariable Long userId);

	// update address
	@PutMapping("/{userId}/{addressId}")
	Object update(Object addressDto, @PathVariable Long userId, @PathVariable Long addressId);

	// delete address
	@DeleteMapping("/{userId}/{addressId}")
	Object delete(@PathVariable Long userId, @PathVariable Long addressId);
}
