package com.cg.gateway.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cg.gateway.dto.ResponseObject;
import com.cg.gateway.service.RemoteAuthService;
import com.google.gson.Gson;

import feign.FeignException.FeignClientException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {
	@Autowired
	private RemoteAuthService remoteAuthService;
	@Autowired
	private Gson gson;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		ResponseObject res = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				res = gson.fromJson(gson.toJson(remoteAuthService.validate(jwtToken)), ResponseObject.class);
				logger.error(res);
			} catch (FeignClientException ex) {
				logger.error(ex.getMessage());
			}
		} else {
			logger.warn("Bearer Token not found in header");
		}

		if (res != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			Map<String, String> data = (Map<String, String>) res.getData();
			UserDetails userDetails = new User(data.get("emailId"), "",
					Arrays.asList(new SimpleGrantedAuthority("ROLE_" + data.get("role"))));

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());

			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(auth);

		}
		chain.doFilter(request, response);
	}

}