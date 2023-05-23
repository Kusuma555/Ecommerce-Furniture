package com.cg.authservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.authservice.dto.ResponseObject;

@FeignClient(name = "user-service", url = "http://localhost:8000/api/user")
public interface RemoteUserService {
	@GetMapping("/credentials/{emailId}")
	ResponseObject get(@PathVariable String emailId);
}
