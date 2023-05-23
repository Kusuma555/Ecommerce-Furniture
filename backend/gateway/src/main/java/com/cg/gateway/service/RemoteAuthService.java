package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="auth-service",url="http://localhost:8001/api/auth")
public interface RemoteAuthService {
	@PostMapping
	Object login(Object request);
	
	@PostMapping("/admin")
	Object adminLogin(Object request);
	
	@GetMapping("/validate/{token}")
	Object validate(@PathVariable String token);
	
	@DeleteMapping("/{token}")
	Object signOut(@PathVariable String token);
}
