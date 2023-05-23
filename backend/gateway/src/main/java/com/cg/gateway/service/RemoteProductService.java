package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "http://localhost:8003/api/product")
public interface RemoteProductService {
	@GetMapping
	Object get();

	@GetMapping("/page/{page}")
	Object get(@PathVariable int page);

	@GetMapping("/search/{query}/{page}")
	Object get(@PathVariable String query, @PathVariable int page);

	@GetMapping("/sort/{by}/{order}/{page}")
	Object get(@PathVariable String by, @PathVariable String order, @PathVariable int page);

	@GetMapping("/{productId}")
	Object get(@PathVariable Long productId);

	@PostMapping
	Object add(@RequestBody Object dto);

	@PutMapping("/{productId}")
	Object update(@PathVariable Long productId, @RequestBody Object dto);

	@DeleteMapping("/{productId}")
	Object delete(@PathVariable Long productId);
}
