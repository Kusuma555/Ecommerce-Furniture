package com.cg.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.gateway.service.RemoteAdService;

@CrossOrigin
@RestController
@RequestMapping("/ad")
public class RemoteAdController {
	@Autowired
	private RemoteAdService service;

	@GetMapping
	public ResponseEntity<?> get() {
		return new ResponseEntity<>(service.get(), HttpStatus.OK);
	}

	@GetMapping("/page/{page}")
	public ResponseEntity<?> get(@PathVariable int page) {
		return new ResponseEntity<>(service.get(page), HttpStatus.OK);
	}

	@GetMapping("/search/{query}/{page}")
	public ResponseEntity<?> get(@PathVariable String query, @PathVariable int page) {
		return new ResponseEntity<>(service.get(query, page), HttpStatus.OK);
	}

	@GetMapping("/sort/{by}/{order}/{page}")
	public ResponseEntity<?> get(@PathVariable String by, @PathVariable String order, @PathVariable int page) {
		return new ResponseEntity<>(service.get(by, order, page), HttpStatus.OK);
	}

	@GetMapping("/{adId}")
	public ResponseEntity<?> get(@PathVariable Long adId) {
		return new ResponseEntity<>(service.get(adId), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/{page}")
	public ResponseEntity<?> get(@PathVariable Long userId, @PathVariable int page) {
		return new ResponseEntity<>(service.get(userId, page), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Object dto) {
		return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{adId}")
	public ResponseEntity<?> update(@PathVariable Long adId, @RequestBody Object dto) {
		return new ResponseEntity<>(service.update(adId, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{adId}")
	public ResponseEntity<?> delete(@PathVariable Long adId) {
		return new ResponseEntity<>(service.delete(adId), HttpStatus.OK);
	}

	@PostMapping("/accept/{adId}")
	public ResponseEntity<?> accept(@PathVariable Long adId) {
		return new ResponseEntity<>(service.accept(adId), HttpStatus.ACCEPTED);
	}

	@PostMapping("/reject/{adId}")
	public ResponseEntity<?> reject(@PathVariable Long adId) {
		return new ResponseEntity<>(service.reject(adId), HttpStatus.ACCEPTED);
	}

}
