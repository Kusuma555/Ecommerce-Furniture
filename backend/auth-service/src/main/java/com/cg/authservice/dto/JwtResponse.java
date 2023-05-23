package com.cg.authservice.dto;

import lombok.Data;

@Data
public class JwtResponse{
	private final String TOKEN;
	private final Long USERID;

}