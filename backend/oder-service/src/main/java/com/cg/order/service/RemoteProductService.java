package com.cg.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.order.dto.ResponseObject;

@FeignClient(name = "category-service", url = "http://localhost:8003/api/product")
public interface RemoteProductService {
	@GetMapping("/{product}")
	ResponseObject get(@PathVariable Long productId);
}
