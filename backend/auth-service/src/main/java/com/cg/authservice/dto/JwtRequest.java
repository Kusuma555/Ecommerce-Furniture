package com.cg.authservice.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class JwtRequest {
	@Email(message = "Email must be valid")
	private String emailId;
	private String password;
}