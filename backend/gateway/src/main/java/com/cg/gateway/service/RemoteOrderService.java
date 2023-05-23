package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "http://localhost:8007/api/order")
public interface RemoteOrderService {
	@GetMapping("/{orderId}")
	Object get(@PathVariable Long orderId);

	@GetMapping("/user/{userId}")
	Object getByUser(@PathVariable Long userId);

	@PostMapping("/{userId}")
	Object add(@PathVariable Long userId, @RequestBody Object dto);

	@PutMapping("/{orderId}/{status}")
	Object updateStatus(@PathVariable Long orderId, @PathVariable Object status);
}
