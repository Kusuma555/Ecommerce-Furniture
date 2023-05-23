package com.cg.authservice.dto;

import lombok.Data;

@Data
public class UserCredentials {
	private Long userId;
	private String emailId;
	private String password;
	private USER_ROLE role;
}