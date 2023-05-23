package com.cg.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ads.dto.ProductAdDto;
import com.cg.ads.service.ProductAdService;
import com.cg.ads.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ad")
public class ProductAdController {
	private static final String BASE_URL = "/api/ad";

	@Autowired
	private ProductAdService service;
	@Autowired
	private ResponseUtil responseUtil;

	@GetMapping
	public ResponseEntity<?> get() {
		return responseUtil.createSuccessResponse(service.get(), BASE_URL, HttpStatus.OK);
	}

	@GetMapping("/page/{page}")
	public ResponseEntity<?> get(@PathVariable int page) {
		return responseUtil.createSuccessResponse(service.get(page), BASE_URL + "/page/" + page, HttpStatus.OK);
	}

	@GetMapping("/search/{query}/{page}")
	public ResponseEntity<?> get(@PathVariable String query, @PathVariable int page) {
		return responseUtil.createSuccessResponse(service.get(query, page), BASE_URL + "/search/" + query + "/" + page,
				HttpStatus.OK);
	}

	@GetMapping("/sort/{by}/{order}/{page}")
	public ResponseEntity<?> get(@PathVariable String by, @PathVariable String order, @PathVariable int page) {
		return responseUtil.createSuccessResponse(service.get(by, order, page),
				BASE_URL + "/sort/" + by + "/" + order + "/" + page, HttpStatus.OK);
	}

	@GetMapping("/{adId}")
	public ResponseEntity<?> get(@PathVariable Long adId) {
		return responseUtil.createSuccessResponse(service.get(adId), BASE_URL + "/" + adId, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/{page}")
	public ResponseEntity<?> get(@PathVariable Long userId, @PathVariable int page) {
		return responseUtil.createSuccessResponse(service.get(userId, page),
				BASE_URL + "/user/" + userId + "/" + page, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> add(@Valid @RequestBody ProductAdDto dto) {
		return responseUtil.createSuccessResponse(service.add(dto), BASE_URL, HttpStatus.CREATED);
	}

	@PutMapping("/{adId}")
	public ResponseEntity<?> update(@PathVariable Long adId, @Valid @RequestBody ProductAdDto dto) {
		dto.setAdId(adId);
		return responseUtil.createSuccessResponse(service.update(dto), BASE_URL + "/" + adId, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{adId}")
	public ResponseEntity<?> delete(@PathVariable Long adId) {
		return responseUtil.createSuccessResponse(service.delete(adId), BASE_URL + "/" + adId, HttpStatus.OK);
	}

	@PostMapping("/accept/{adId}")
	public ResponseEntity<?> accept(@PathVariable Long adId) {
		return responseUtil.createSuccessResponse(service.accept(adId), BASE_URL + "/accept/" + adId,
				HttpStatus.ACCEPTED);
	}

	@PostMapping("/reject/{adId}")
	public ResponseEntity<?> reject(@PathVariable Long adId) {
		return responseUtil.createSuccessResponse(service.reject(adId), BASE_URL + "/accept/" + adId,
				HttpStatus.ACCEPTED);
	}

}
