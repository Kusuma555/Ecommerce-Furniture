package com.cg.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, TokenFilter filter,
			AuthEntryPoint authenticationEntryPoint) throws Exception {

		http.cors().and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests(authorize -> authorize      
//						.requestMatchers("/user/**").hasRole("USER")
						.anyRequest().permitAll()                                            
					);
//				.authorizeHttpRequests().requestMatchers("/test/**").permitAll();

		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

		return http.build();
	}
}