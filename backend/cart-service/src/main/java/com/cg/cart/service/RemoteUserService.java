package com.cg.cart.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.cart.dto.ResponseObject;

@FeignClient(name = "user-service", url = "http://localhost:8000/api/user")
public interface RemoteUserService {
	@GetMapping("/id/{userId}")
	ResponseObject get(@PathVariable Long userId);
}
