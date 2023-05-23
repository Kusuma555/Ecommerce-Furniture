package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-category-service", url = "http://localhost:8003/api/category")
public interface RemoteProductCategoryService {
	@GetMapping
	Object get();

	@GetMapping("/{categoryId}")
	Object get(@PathVariable Long categoryId);

	@PostMapping
	Object add(@RequestBody Object dto);

	@PutMapping("/{categoryId}")
	Object update(@PathVariable Long categoryId, @RequestBody Object dto);

	@DeleteMapping("/{categoryId}")
	Object delete(@PathVariable Long categoryId);
}
