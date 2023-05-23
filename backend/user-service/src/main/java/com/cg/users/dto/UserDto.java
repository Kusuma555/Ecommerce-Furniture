package com.cg.users.dto;

import com.cg.users.entity.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto{
	@JsonProperty(access = Access.READ_ONLY)
	private Long userId;
	@NotBlank(message = "First Name must not be blank")
	private String firstName;
	@NotBlank(message = "Last Name must not be blank")
	private String lastName;
	@NotBlank(message = "Gender must not be blank")
	private String gender;
	@NotNull(message = "Mobile Number must not be blank")
	@Size(min = 10,max = 10)
	private String mobileNumber;
	
	@JsonProperty(access = Access.READ_ONLY)
	private USER_ROLE role = USER_ROLE.USER;
	
	@Email(message = "Email must be valid")
	private String emailId;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "Passowrd must not be blank")
	@Size(min = 5,max = 20, message = "Password must be >= 5 and <=20")
	private String password;
}
