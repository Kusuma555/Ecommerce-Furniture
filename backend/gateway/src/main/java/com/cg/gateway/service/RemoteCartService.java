package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cart-service", url = "http://localhost:8006/api/cart")
public interface RemoteCartService {
	@GetMapping("/{userId}")
	Object get(@PathVariable Long userId);
	
	@PostMapping("/{userId}")
	Object update(@PathVariable Long userId, @RequestBody Object dto);

	@DeleteMapping("/{userId}/{productId}")
	Object remove(@PathVariable Long userId, @PathVariable Long productId);
}
