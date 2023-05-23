package com.cg.users.dto;

import com.cg.users.entity.USER_ROLE;

import lombok.Data;

@Data
public class UserCredentials {
	private Long userId;
	private String emailId;
	private String password;
	private USER_ROLE role;
}