package com.cg.authservice.service;

import com.cg.authservice.dto.JwtRequest;

public interface AuthService {
	Object signIn(JwtRequest request);
	
	Object adminSignIn(JwtRequest request);

	Object signOut(String token);

	Object validateToken(String token);
}
