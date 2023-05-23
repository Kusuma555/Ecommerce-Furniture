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

import com.cg.gateway.service.RemoteProductCategoryService;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class RemoteProductCategoryController {
	@Autowired
	private RemoteProductCategoryService service;

	@GetMapping
	public ResponseEntity<?> get() {
		return new ResponseEntity<>(service.get(), HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<?> get(@PathVariable Long categoryId) {
		return new ResponseEntity<>(service.get(categoryId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Object dto) {
		return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<?> update(@PathVariable Long categoryId, @RequestBody Object dto) {
		return new ResponseEntity<>(service.update(categoryId, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> delete(@PathVariable Long categoryId) {
		return new ResponseEntity<>(service.delete(categoryId), HttpStatus.OK);
	}

}
