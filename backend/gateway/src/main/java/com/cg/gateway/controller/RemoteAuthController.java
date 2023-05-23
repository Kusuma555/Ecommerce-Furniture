package com.cg.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.gateway.service.RemoteAuthService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class RemoteAuthController {
	@Autowired
	private RemoteAuthService authService;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody Object request) {
		return new ResponseEntity<>(authService.login(request), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/admin")
	public ResponseEntity<?> adminLogin(@RequestBody Object request) {
		return new ResponseEntity<>(authService.adminLogin(request), HttpStatus.ACCEPTED);
	}

	@GetMapping("/validate/{token}")
	public ResponseEntity<?> validate(@PathVariable String token) {
		return new ResponseEntity<>(authService.validate(token), HttpStatus.OK);
	}

	@DeleteMapping("/{token}")
	public ResponseEntity<?> signOut(@PathVariable String token) {
		return new ResponseEntity<>(authService.signOut(token), HttpStatus.OK);
	}
}
