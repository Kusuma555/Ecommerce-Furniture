package com.cg.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.authservice.dto.JwtRequest;
import com.cg.authservice.service.AuthService;
import com.cg.authservice.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final String BASE_URL = "/api/auth";

	@Autowired
	private AuthService authService;

	@Autowired
	private ResponseUtil responseUtil;

	@PostMapping
	public ResponseEntity<?> login(@Valid @RequestBody JwtRequest request) {
		return responseUtil.createSuccessResponse(authService.signIn(request), BASE_URL, HttpStatus.ACCEPTED);
	}

	@PostMapping("/admin")
	public ResponseEntity<?> adminLogin(@Valid @RequestBody JwtRequest request) {
		return responseUtil.createSuccessResponse(authService.adminSignIn(request), BASE_URL, HttpStatus.ACCEPTED);
	}

	
	@GetMapping("/validate/{token}")
	public ResponseEntity<?> validate(@PathVariable String token) {
		return responseUtil.createSuccessResponse(authService.validateToken(token), BASE_URL + "/validate/" + token,
				HttpStatus.OK);
	}

	@DeleteMapping("/{token}")
	public ResponseEntity<?> signOut(@PathVariable String token) {
		return responseUtil.createSuccessResponse(authService.signOut(token), BASE_URL + "/" + token, HttpStatus.OK);
	}
}
