package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ad-service", url = "http://localhost:8005/api/ad")
public interface RemoteAdService {
	@GetMapping
	Object get();

	@GetMapping("/page/{page}")
	Object get(@PathVariable int page);

	@GetMapping("/search/{query}/{page}")
	Object get(@PathVariable String query, @PathVariable int page);

	@GetMapping("/sort/{by}/{order}/{page}")
	Object get(@PathVariable String by, @PathVariable String order, @PathVariable int page);

	@GetMapping("/{adId}")
	Object get(@PathVariable Long adId);

	@GetMapping("/user/{userId}/{page}")
	Object get(@PathVariable Long userId, @PathVariable int page);

	@PostMapping
	Object add(@RequestBody Object dto);

	@PutMapping("/{adId}")
	Object update(@PathVariable Long adId, @RequestBody Object dto);

	@DeleteMapping("/{adId}")
	Object delete(@PathVariable Long adId);

	@PostMapping("/accept/{adId}")
	Object accept(@PathVariable Long adId);

	@PostMapping("/reject/{adId}")
	Object reject(@PathVariable Long adId);
}
