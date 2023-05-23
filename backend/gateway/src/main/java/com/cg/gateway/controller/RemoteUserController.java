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

import com.cg.gateway.service.RemoteUserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class RemoteUserController {
	@Autowired
	private RemoteUserService userService;

	// get user
	@GetMapping("/id/{userId}")
	public ResponseEntity<?> get(@PathVariable Long userId) {
		return new ResponseEntity<>(userService.get(userId), HttpStatus.OK);
	}

	// get user
	@GetMapping("/email/{emailId}")
	public ResponseEntity<?> get(@PathVariable String emailId) {
		return new ResponseEntity<>(userService.get(emailId), HttpStatus.OK);
	}

	// get users
	@GetMapping
	public ResponseEntity<?> get() {
		return new ResponseEntity<>(userService.get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Object userDto) {
		return new ResponseEntity<>(userService.add(userDto), HttpStatus.CREATED);
	}

	// update user
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Object userDto) {
		return new ResponseEntity<>(userService.update(id, userDto), HttpStatus.ACCEPTED);
	}

	// delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
	}

}
