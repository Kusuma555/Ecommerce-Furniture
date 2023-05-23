package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="user-service",url="http://localhost:8000/api/user")
public interface RemoteUserService {
	@GetMapping
	Object get();
	
	@GetMapping("/id/{userId}")
	Object get(@PathVariable Long userId);

	@GetMapping("/email/{emailId}")
	Object get(@PathVariable String emailId);
	
	@PostMapping
	Object add(Object request);

	@PutMapping("/{id}")
	Object update(@PathVariable Long id,Object request);

	@DeleteMapping("/{id}")
	Object delete(@PathVariable Long id);

	@PutMapping
	Object changePassword(Object request);
}
