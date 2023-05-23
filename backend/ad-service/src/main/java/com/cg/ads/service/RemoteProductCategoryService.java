package com.cg.ads.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.ads.dto.ResponseObject;

@FeignClient(name = "category-service", url = "http://localhost:8003/api/category")
public interface RemoteProductCategoryService {
	@GetMapping("/{categoryId}")
	ResponseObject get(@PathVariable Long categoryId);
}
