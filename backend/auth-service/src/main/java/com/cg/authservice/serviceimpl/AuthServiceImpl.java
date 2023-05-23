package com.cg.authservice.serviceimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cg.authservice.dto.JwtRequest;
import com.cg.authservice.dto.JwtResponse;
import com.cg.authservice.dto.ResponseObject;
import com.cg.authservice.dto.USER_ROLE;
import com.cg.authservice.entity.UserToken;
import com.cg.authservice.repository.UserTokenRepo;
import com.cg.authservice.service.AuthService;
import com.cg.authservice.service.RemoteUserService;
import com.cg.authservice.util.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private UserTokenRepo tokenRepo;

	@Transactional
	@Override
	public Object signIn(JwtRequest request) {
		try {
			authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));
		} catch (DisabledException e) {
			throw new RuntimeException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			throw new RuntimeException("INVALID_CREDENTIALS");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmailId());

		final String token = jwtUtil.generateToken(userDetails);

		final Long userId = userDetailsService.getUser().getUserId();
		// save token
		tokenRepo.deleteByUserId(userId);

		UserToken userToken = new UserToken();
		userToken.setToken(token);
		userToken.setUserId(userId);

		tokenRepo.save(userToken);

		return new JwtResponse(token,userId);
	}
	
	@Transactional
	@Override
	public Object adminSignIn(JwtRequest request) {
		try {
			authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));
		} catch (DisabledException e) {
			throw new RuntimeException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			throw new RuntimeException("INVALID_CREDENTIALS");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmailId());
		
		if(userDetailsService.getUser().getRole() != USER_ROLE.ADMIN)
			throw new RuntimeException("User is not an Admin");

		final String token = jwtUtil.generateToken(userDetails);

		final Long userId = userDetailsService.getUser().getUserId();
		// save token
		tokenRepo.deleteByUserId(userId);

		UserToken userToken = new UserToken();
		userToken.setToken(token);
		userToken.setUserId(userId);

		tokenRepo.save(userToken);

		return new JwtResponse(token,userId);
	}

	@Transactional
	@Override
	public Object signOut(String token) {
		UserToken userToken = tokenRepo.findByToken(token).orElseThrow(()->new RuntimeException("Token not found"));
		return tokenRepo.deleteByUserId(userToken.getUserId());
	}

	@Override
	public Object validateToken(String token) {
		// check token in database
		tokenRepo.findByToken(token).orElseThrow(() -> new RuntimeException("Used token"));

		String username = jwtUtil.getUsernameFromToken(token);
		ResponseObject res = remoteUserService.get(username);

		Map<String, String> data = (Map<String, String>) res.getData();
		
		boolean valid = jwtUtil.validateToken(token, data.get("emailId"));

		return valid ? res.getData() : null;
	}

}
