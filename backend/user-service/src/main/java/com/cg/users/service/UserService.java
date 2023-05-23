package com.cg.users.service;

import java.util.List;

import com.cg.users.dto.UserCredentials;
import com.cg.users.dto.UserDto;

public interface UserService {
	UserDto get(Long userId);
	
	UserDto get(String email);
	
	UserCredentials getCredntials(String email);
	
	List<UserDto> get();
	
	UserDto add(UserDto userDto);

	UserDto update(UserDto userDto);

	UserDto delete(Long id);
}
