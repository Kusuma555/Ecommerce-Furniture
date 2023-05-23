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

import com.cg.gateway.service.RemoteProductService;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class RemoteProductController {
	@Autowired
	private RemoteProductService service;

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

	@GetMapping("/{productId}")
	public ResponseEntity<?> get(@PathVariable Long productId) {
		return new ResponseEntity<>(service.get(productId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Object dto) {
		return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<?> update(@PathVariable Long productId, @RequestBody Object dto) {
		return new ResponseEntity<>(service.update(productId, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<?> delete(@PathVariable Long productId) {
		return new ResponseEntity<>(service.delete(productId), HttpStatus.OK);
	}

}
