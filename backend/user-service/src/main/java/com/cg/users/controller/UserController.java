package com.cg.users.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.users.dto.UserDto;
import com.cg.users.service.UserService;
import com.cg.users.util.ResponseUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final String BASE_URL ="/api/user";

	@Autowired
	private UserService userService;
	@Autowired
	private ResponseUtil responseUtil;

	// get user
	@GetMapping("/id/{userId}")
	public ResponseEntity<?> get(@PathVariable Long userId) {
		return responseUtil.createSuccessResponse(userService.get(userId), BASE_URL + "/" + userId, HttpStatus.OK);
	}

	// get user
	@GetMapping("/email/{emailId}")
	public ResponseEntity<?> get(@PathVariable String emailId) {
		return responseUtil.createSuccessResponse(userService.get(emailId), BASE_URL + "/" + emailId, HttpStatus.OK);
	}

	@GetMapping("/credentials/{emailId}")
	public ResponseEntity<?> getCredentials(@PathVariable String emailId) {
		return responseUtil.createSuccessResponse(userService.getCredntials(emailId),
				BASE_URL + "/credentials/" + emailId, HttpStatus.OK);
	}

	// get users
	@GetMapping
	public ResponseEntity<?> get() {
		return responseUtil.createSuccessResponse(userService.get(), BASE_URL, HttpStatus.OK);
	}

	// add user
	@PostMapping
	public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto) {
		return responseUtil.createSuccessResponse(userService.add(userDto), BASE_URL, HttpStatus.CREATED);
	}

	// update user
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserDto userDto) {
		userDto.setUserId(id);
		return responseUtil.createSuccessResponse(userService.update(userDto), BASE_URL + "/" + id,
				HttpStatus.ACCEPTED);
	}

	// delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return responseUtil.createSuccessResponse(userService.delete(id), BASE_URL + "/" + id, HttpStatus.OK);
	}

}
