package com.cg.authservice.serviceimpl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.authservice.dto.ResponseObject;
import com.cg.authservice.dto.UserCredentials;
import com.cg.authservice.service.RemoteUserService;
import com.google.gson.Gson;

import lombok.Getter;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private Gson gson;

	@Getter
	private UserCredentials user;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ResponseObject response = remoteUserService.get(username);

		if (response.isError())
			throw new UsernameNotFoundException(response.getMessage());

		user = gson.fromJson(gson.toJson(response.getData()), UserCredentials.class);
		System.err.println(user);

		return new User(user.getEmailId(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
	}
}