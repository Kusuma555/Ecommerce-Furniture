package com.cg.authservice.dto;

public enum USER_ROLE{
	ADMIN,
	USER,
	UNDEFINED;
	
	
	public static USER_ROLE set(String string) {
		string = string.toLowerCase();
		if (string.equals("user"))
			return USER;
		else if (string.equals("admin"))
			return ADMIN;

		return USER_ROLE.UNDEFINED;
	}
}